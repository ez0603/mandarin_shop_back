package com.example.mandarin_shop_back.service.user;

import com.example.mandarin_shop_back.dto.account.request.EditPasswordReqDto;
import com.example.mandarin_shop_back.entity.account.Admin;
import com.example.mandarin_shop_back.entity.user.User;
import com.example.mandarin_shop_back.exception.ValidException;
import com.example.mandarin_shop_back.repository.AdminMapper;
import com.example.mandarin_shop_back.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class UserAccountService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void editPassword(EditPasswordReqDto editPasswordReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userMapper.findUserByUsername(authentication.getName());
        if (user == null) {
            throw new ValidException(Map.of("error", "계정을 찾을 수 없습니다."));
        }

        if (!passwordEncoder.matches(editPasswordReqDto.getOldPassword(), user.getPassword())) {
            throw new ValidException(Map.of("oldPassword", "비밀번호 인증에 실패하였습니다. \n다시 입력해주세요."));
        }

        if (!editPasswordReqDto.getNewPassword().equals(editPasswordReqDto.getNewPasswordCheck())) {
            throw new ValidException(Map.of("newPasswordCheck", "새로운 비밀번호가 서로 일치하지 않습니다.\n다시 입력해주세요."));
        }

        if (passwordEncoder.matches(editPasswordReqDto.getNewPassword(), user.getPassword())) {
            throw new ValidException(Map.of("newPassword", "이전 비밀번호와 동일한 비밀번호는 사용하실 수 없습니다.\n다시 입력해주세요."));
        }

        // 비밀번호 업데이트
        user.setPassword(passwordEncoder.encode(editPasswordReqDto.getNewPassword()));
        userMapper.modifyUserPassword(user); // 실제 데이터베이스에 업데이트하는 코드

        // 세션 무효화 또는 재인증 처리
        SecurityContextHolder.clearContext(); // 현재 세션을 무효화합니다.
    }

    public String findAccountUserByNameAndEmail(String customerName, String email) {
        User user = userMapper.findAccountUserByNameAndEmail(customerName, email);
        if (user != null) {
            return user.getUsername(); // 아이디가 저장된 필드 사용
        }
        return null; // 계정이 존재하지 않을 경우 null 반환 또는 적절한 처리
    }

}
