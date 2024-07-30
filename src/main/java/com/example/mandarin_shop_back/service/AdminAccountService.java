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
        String adminName = (authentication != null) ? authentication.getName() : null;
        System.out.println("Authentication object: " + authentication);
        System.out.println("Admin name: " + adminName);

        if (adminName == null || adminName.isEmpty()) {
            throw new ValidException(Map.of("error", "계정을 찾을 수 없습니다."));
        }

        Admin admin = adminMapper.findAdminByUsername(adminName);
        if (admin == null) {
            throw new ValidException(Map.of("error", "계정을 찾을 수 없습니다."));
        }


        if (!passwordEncoder.matches(editPasswordReqDto.getOldPassword(), admin.getAdminPassword())) {
            throw new ValidException(Map.of("oldPassword", "비밀번호 인증에 실패하였습니다. \n다시 입력해주세요."));
        }

        if (!editPasswordReqDto.getNewPassword().equals(editPasswordReqDto.getNewPasswordCheck())) {
            throw new ValidException(Map.of("newPasswordCheck", "새로운 비밀번호가 서로 일치하지 않습니다.\n다시 입력해주세요."));
        }

        if (passwordEncoder.matches(editPasswordReqDto.getNewPassword(), admin.getAdminPassword())) {
            throw new ValidException(Map.of("newPassword", "이전 비밀번호와 동일한 비밀번호는 사용하실 수 없습니다.\n다시 입력해주세요."));
        }

        // 비밀번호 업데이트
        admin.setAdminPassword(passwordEncoder.encode(editPasswordReqDto.getNewPassword()));
//        adminMapper.modifyPassword(admin); // 또는 이와 유사한 데이터베이스 업데이트 메서드 호출
    }

    public String findAccountByNameAndEmail(String name, String email) {
        Admin admin = adminMapper.findAccountByNameAndEmail(name, email);
        if (admin != null) {
            return admin.getAdminName(); // 아이디가 저장된 필드 사용
        }
        return null; // 계정이 존재하지 않을 경우 null 반환 또는 적절한 처리
    }
}
