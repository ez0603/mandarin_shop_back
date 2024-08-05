package com.example.mandarin_shop_back.service.admin;

import com.example.mandarin_shop_back.dto.account.request.AdminSignupReqDto;
import com.example.mandarin_shop_back.entity.account.Admin;
import com.example.mandarin_shop_back.entity.user.User;
import com.example.mandarin_shop_back.exception.SaveException;
import com.example.mandarin_shop_back.jwt.JwtProvider;
import com.example.mandarin_shop_back.repository.AdminMapper;
import com.example.mandarin_shop_back.service.user.UserAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminAuthService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserAuthService.class);

    @Transactional(rollbackFor = Exception.class)
    public void adminSignup(AdminSignupReqDto adminSignupReqDto) {
        Admin admin = adminSignupReqDto.toEntity(passwordEncoder);

        int successCount = adminMapper.saveAdmin(admin);
        successCount += adminMapper.saveRole(admin.getAdminId(), 1);

        if (successCount < 2) {
            throw new SaveException();
        }
    }

    public String adminSignin(AdminSignupReqDto adminSignupReqDto) {
        logger.info("로그인 요청: {}", adminSignupReqDto);

        try {
            Admin admin = adminMapper.findAdminByUsername(adminSignupReqDto.getAdminName());
            if (admin == null) {
                logger.error("사용자 정보를 확인하세요. 사용자 이름: {}", adminSignupReqDto.getAdminName());
                throw new UsernameNotFoundException("사용자 정보를 확인하세요");
            }
            if (!passwordEncoder.matches(adminSignupReqDto.getAdminPassword(), admin.getAdminPassword())) {
                logger.error("비밀번호가 일치하지 않습니다. 사용자 이름: {}", adminSignupReqDto.getAdminName());
                throw new BadCredentialsException("사용자 정보를 확인하세요");
            }

            String token = jwtProvider.generateAdminToken(admin);
            logger.info("토큰 발급 성공: {}", token);
            return token;
        } catch (Exception e) {
            logger.error("로그인 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        }
    }
}
