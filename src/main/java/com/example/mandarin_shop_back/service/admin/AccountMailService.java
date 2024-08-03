package com.example.mandarin_shop_back.service.admin;

import com.example.mandarin_shop_back.entity.account.Admin;
import com.example.mandarin_shop_back.entity.user.User;
import com.example.mandarin_shop_back.repository.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AccountMailService {

    private static final long AUTH_CODE_EXPIRATION = 60 * 3L; // 3 minutes
    private static final String AUTH_SUBJECT = "[Table Maid] 계정 메일 인증";
    private static final String PASSWORD_RESET_SUBJECT = "임시 비밀번호 발급";
    private static final String ACCOUNT_FIND_SUBJECT = "[Table Maid] 계정 찾기";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.address}")
    private String fromMailAddress;

    private static final Logger logger = LoggerFactory.getLogger(AccountMailService.class);

    // In-memory store for email verification codes
    private final Map<String, String> emailAuthCodeMap = new ConcurrentHashMap<>();
    private final Map<String, Long> emailAuthCodeExpiryMap = new ConcurrentHashMap<>();

    private String createAuthCode() {
        int leftLimit = 48; // number '0'
        int rightLimit = 122; // alphabet 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private void sendEmail(String to, String subject, String content) throws MessagingException {
        if (fromMailAddress == null || fromMailAddress.isEmpty()) {
            throw new IllegalArgumentException("발신자 이메일 주소가 설정되지 않았습니다.");
        }
        logger.info("Sending email to: {}", to);

        JavaMailSenderImpl mailSenderImpl = (JavaMailSenderImpl) javaMailSender;
        logger.info("SMTP Host: {}", mailSenderImpl.getJavaMailProperties().getProperty("mail.smtp.host"));
        logger.info("SMTP Port: {}", mailSenderImpl.getJavaMailProperties().getProperty("mail.smtp.port"));
        logger.info("From Address: {}", fromMailAddress);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setSubject(subject);
        helper.setFrom(fromMailAddress);
        helper.setTo(to);
        mimeMessage.setText(content, "utf-8", "html");
        javaMailSender.send(mimeMessage);
    }

    public boolean sendAuthMail(String email) {
        String authCode = createAuthCode();
        String mailContent = "<div><h1>Mandarin Shop</h1><div><h3>인증번호는 " + authCode + "입니다</h3></div></div>";
        try {
            sendEmail(email, AUTH_SUBJECT, mailContent);
            emailAuthCodeMap.put(email, authCode);
            emailAuthCodeExpiryMap.put(email, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(AUTH_CODE_EXPIRATION));
            return true;
        } catch (MessagingException e) {
            logger.error("Failed to send authentication email", e);
            return false;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid email configuration", e);
            return false;
        }
    }

    public User searchAccountByNameAndEmail(String name, String email) {
        return userMapper.findAccountByNameAndEmail(name, email);
    }

    public Map<String, String> verifyEmailCode(String email, String code) {
        Long expiryTime = emailAuthCodeExpiryMap.get(email);
        if (expiryTime == null || System.currentTimeMillis() > expiryTime) {
            emailAuthCodeMap.remove(email);
            emailAuthCodeExpiryMap.remove(email);
            return Map.of("status", "fail", "message", "인증 시간을 초과하였습니다.");
        }

        String authCode = emailAuthCodeMap.get(email);
        if (authCode != null && Objects.equals(authCode, code)) {
            emailAuthCodeMap.remove(email);
            emailAuthCodeExpiryMap.remove(email);
            return Map.of("status", "success", "message", "이메일 인증에 성공하였습니다.");
        } else {
            return Map.of("status", "fail", "message", "인증번호가 일치하지 않습니다.");
        }
    }

    public boolean searchAccountByMail(User user) {
        if (user == null) return false;

        String mailContent = "<div><h1>Mandarin Shop</h1><div><h3>귀하의 아이디는 " + user.getUsername() + "입니다</h3></div></div>";
        try {
            sendEmail(user.getEmail(), ACCOUNT_FIND_SUBJECT, mailContent);
            return true;
        } catch (MessagingException e) {
            logger.error("Failed to send account search email", e);
            return false;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid email configuration", e);
            return false;
        }
    }

    public User searchAccountByUsernameAndEmail(String username, String email) {
        return userMapper.findAccountByNameAndEmail(username, email);
    }

    public boolean sendTemporaryPassword(User user) {
        if (user == null) return false;

        String temporaryPassword = generateTemporaryPassword();
        String encodedPassword = passwordEncoder.encode(temporaryPassword);
        String mailContent = "<div><h1>Mandarin Shop</h1><div><p>안녕하세요, " + user.getUsername() + "님!</p>"
                + "<p>임시 비밀번호는 다음과 같습니다: <strong>" + temporaryPassword + "</strong></p>"
                + "<p>로그인 후에 비밀번호를 변경해주세요.</p></div></div>";
        try {
            sendEmail(user.getEmail(), PASSWORD_RESET_SUBJECT, mailContent);
            userMapper.updateUserAccountTemporaryPw(user.getUserId(), encodedPassword);
            return true;
        } catch (MessagingException e) {
            logger.error("Failed to send temporary password email", e);
            return false;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid email configuration", e);
            return false;
        }
    }

    private String generateTemporaryPassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
