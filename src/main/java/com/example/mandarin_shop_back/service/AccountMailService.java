package com.example.mandarin_shop_back.service;

import com.example.mandarin_shop_back.entity.account.Admin;
import com.example.mandarin_shop_back.repository.AdminMapper;
import com.example.mandarin_shop_back.util.RedisUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service
public class AccountMailService {

    private static final long AUTH_CODE_EXPIRATION = 60 * 3L; // 3 minutes
    private static final String AUTH_SUBJECT = "[Table Maid] 계정 메일 인증";
    private static final String PASSWORD_RESET_SUBJECT = "임시 비밀번호 발급";
    private static final String ACCOUNT_FIND_SUBJECT = "[Table Maid] 계정 찾기";

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${spring.mail.address}")
    private String fromMailAddress;

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
        String mailContent = "<div><h1>Table Maid</h1><div><h3>인증번호는 " + authCode + "입니다</h3></div></div>";
        try {
            sendEmail(email, AUTH_SUBJECT, mailContent);
            redisUtil.setDataExpire(email, authCode, AUTH_CODE_EXPIRATION);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, String> verifyEmailCode(String email, String code) {
        String authCode = redisUtil.getData(email);
        if (authCode != null && Objects.equals(authCode, code)) {
            return Map.of("status", "success", "message", "이메일 인증에 성공하였습니다.");
        } else if (authCode == null) {
            return Map.of("status", "fail", "message", "인증 시간을 초과하였습니다.");
        } else {
            return Map.of("status", "fail", "message", "인증번호가 일치하지 않습니다.");
        }
    }

    public Admin searchAccountByNameAndEmail(String name, String email) {
        return adminMapper.findAccountByNameAndEmail(name, email);
    }

    public boolean searchAccountByMail(Admin admin) {
        if (admin == null) return false;

        String mailContent = "<div><h1>Table Maid</h1><div><h3>귀하의 아이디는 " + admin.getAdminName() + "입니다</h3></div></div>";
        try {
            sendEmail(admin.getEmail(), ACCOUNT_FIND_SUBJECT, mailContent);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Admin searchAccountByUsernameAndEmail(String username, String email) {
        return adminMapper.findAccountByUserNameAndEmail(username, email);
    }

    public boolean sendTemporaryPassword(Admin admin) {
        if (admin == null) return false;

        String temporaryPassword = generateTemporaryPassword();
        String encodedPassword = passwordEncoder.encode(temporaryPassword);
        String mailContent = "<div><h1>Table Maid</h1><div><p>안녕하세요, " + admin.getAdminName() + "님!</p>"
                + "<p>임시 비밀번호는 다음과 같습니다: <strong>" + temporaryPassword + "</strong></p>"
                + "<p>로그인 후에 비밀번호를 변경해주세요.</p></div></div>";
        try {
            sendEmail(admin.getEmail(), PASSWORD_RESET_SUBJECT, mailContent);
            adminMapper.updateAccountTemporaryPw(admin.getAdminId(), encodedPassword);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateTemporaryPassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
