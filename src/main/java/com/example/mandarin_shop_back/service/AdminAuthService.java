package com.example.mandarin_shop_back.service;

import com.example.mandarin_shop_back.dto.account.request.AdminSignupReqDto;
import com.example.mandarin_shop_back.entity.account.Admin;
import com.example.mandarin_shop_back.exception.SaveException;
import com.example.mandarin_shop_back.jwt.JwtProvider;
import com.example.mandarin_shop_back.repository.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AdminAuthService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public void adminSignup(AdminSignupReqDto adminSignupReqDto) {
        System.out.println("서비스 진입");
        int successCount = 0;
        Admin admin = adminSignupReqDto.toEntity(passwordEncoder);

        successCount += adminMapper.saveAdmin(admin);

        if(successCount < 1) {
            throw new SaveException(Map.of("adminSignup 오류", "정상적으로 회원가입이 되지 않았습니다."));
        }
    }

    public String adminSignin(AdminSignupReqDto adminSignupReqDto) {
        Admin admin = adminMapper.findAdminByUsername(adminSignupReqDto.getName());
        if( admin == null) {
            throw new UsernameNotFoundException("사용자 정보를 확인하세요");
        }
        if(!passwordEncoder.matches(adminSignupReqDto.getAdminPassword(), admin.getAdminPassword())) {
            throw new BadCredentialsException("사용자 정보를 확인하세요");
        }
        System.out.println(123);
        return jwtProvider.generateToken(admin);
    }
}
