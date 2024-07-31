package com.example.mandarin_shop_back.service.user;

import com.example.mandarin_shop_back.entity.user.User;
import com.example.mandarin_shop_back.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public boolean userExists(int userId) {
        // userMapper를 사용하여 userId가 존재하는지 확인
        User user = userMapper.findUserByUserId(userId);
        return user != null;
    }
}
