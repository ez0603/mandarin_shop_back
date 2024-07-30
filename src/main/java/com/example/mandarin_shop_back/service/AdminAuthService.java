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
    private AdminMapper adminMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
        Admin admin = adminMapper.findAdminByUsername(adminSignupReqDto.getAdminName());
        if (admin == null) {
            throw new UsernameNotFoundException("사용자 정보를 확인하세요");
        }
        if (!passwordEncoder.matches(adminSignupReqDto.getAdminPassword(), admin.getAdminPassword())) {
            throw new BadCredentialsException("사용자 정보를 확인하세요");
        }
        return jwtProvider.generateToken(admin);
    }
}
