package com.example.mandarin_shop_back.service.user;

import com.example.mandarin_shop_back.dto.user.request.UserSignupReqDto;
import com.example.mandarin_shop_back.entity.user.User;
import com.example.mandarin_shop_back.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAuthService {
    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class) // 전화번호 가입
    public void signup(UserSignupReqDto userSignupReqDto) {
        User user = userSignupReqDto.toEntity();
        userMapper.saveUser(user);
        System.out.println("유저 서비스옴? ");
    }
}
