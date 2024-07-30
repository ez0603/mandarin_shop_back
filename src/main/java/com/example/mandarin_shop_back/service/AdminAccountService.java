package com.example.mandarin_shop_back.service;

import com.example.mandarin_shop_back.dto.account.request.EditPasswordReqDto;
import com.example.mandarin_shop_back.entity.account.Admin;
import com.example.mandarin_shop_back.exception.ValidException;
import com.example.mandarin_shop_back.repository.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AdminAccountService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void editPassword(EditPasswordReqDto editPasswordReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminMapper.findAdminByUsername(authentication.getName());
        if(!passwordEncoder.matches(editPasswordReqDto.getOldPassword(),admin.getAdminPassword())) {
            throw new ValidException(Map.of("oldPassword", "비밀번호 인증에 실패하였습니다. \n다시입력해주세요."));
        }
        if(!editPasswordReqDto.getNewPassword().equals(editPasswordReqDto.getNewPasswordCheck())) {
            throw new ValidException(Map.of("newPasswordCheck", "새로운 비밀번호가 서로 일치하지 않습니다.\n다시입력해주세요."));
        }
        if(passwordEncoder.matches(editPasswordReqDto.getNewPassword(),admin.getAdminPassword())) {
            throw new ValidException(Map.of("newPassword","이전 비밀번호와 동일한 비밀번호는 사용하실 수 없습니다.\n다시입력해주세요."));
        }

        admin.setAdminPassword(passwordEncoder.encode(editPasswordReqDto.getNewPassword()));

    }

    public String findAccountByNameAndEmail(String name, String email) {
        String aminaName = adminMapper.findAccountByNameAndEmail(name, email).getAdminName();
        return aminaName;
    }
}
