package com.example.mandarin_shop_back.service.user;

import com.example.mandarin_shop_back.dto.user.request.UserSignupReqDto;
import com.example.mandarin_shop_back.entity.user.User;
import com.example.mandarin_shop_back.exception.CustomException;
import com.example.mandarin_shop_back.jwt.JwtProvider;
import com.example.mandarin_shop_back.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
@Service
public class UserAuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    private static final Logger logger = LoggerFactory.getLogger(UserAuthService.class);

    @Transactional(rollbackFor = Exception.class)
    public void signup(UserSignupReqDto userSignupReqDto) {
        logger.info("회원가입 요청: {}", userSignupReqDto);

        User user = userSignupReqDto.toEntity();
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userSignupReqDto.getPassword());
        user.setPassword(encodedPassword);

        try {
            userMapper.saveUser(user);
            logger.info("회원가입 성공: {}", user);
        } catch (DataIntegrityViolationException e) {
            String message = e.getMostSpecificCause().getMessage();
            if (message.contains("user_tb.username_UNIQUE")) {
                logger.error("이미 사용 중인 아이디입니다.");
                throw new CustomException("이미 사용 중인 아이디입니다.");
            } else if (message.contains("user_tb.email_UNIQUE")) {
                logger.error("이미 사용 중인 이메일입니다.");
                throw new CustomException("이미 사용 중인 이메일입니다.");
            } else if (message.contains("user_tb.phone_UNIQUE")) {
                logger.error("이미 사용 중인 휴대폰 번호입니다.");
                throw new CustomException("이미 사용 중인 휴대폰 번호입니다.");
            }
            logger.error("회원가입 중 오류 발생: {}", message);
            throw e;
        }
    }

    public String userSignin(UserSignupReqDto userSignupReqDto) {
        logger.info("로그인 요청: {}", userSignupReqDto);

        try {
            User user = userMapper.findUserByUsername(userSignupReqDto.getUsername());
            if (user == null) {
                logger.error("사용자 정보를 확인하세요. 사용자 이름: {}", userSignupReqDto.getUsername());
                throw new UsernameNotFoundException("사용자 정보를 확인하세요");
            }
            if (!passwordEncoder.matches(userSignupReqDto.getPassword(), user.getPassword())) {
                logger.error("비밀번호가 일치하지 않습니다. 사용자 이름: {}", userSignupReqDto.getUsername());
                throw new BadCredentialsException("사용자 정보를 확인하세요");
            }

            String token = jwtProvider.generateUserToken(user);
            logger.info("토큰 발급 성공: {}", token);
            return token;
        } catch (Exception e) {
            logger.error("로그인 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<User> getAllUser() {
        return userMapper.getUserAuth();
    }
}
