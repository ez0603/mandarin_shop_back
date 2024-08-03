package com.example.mandarin_shop_back.service.user;

import com.example.mandarin_shop_back.dto.product.response.OptionsRespDto;
import com.example.mandarin_shop_back.dto.user.request.UserSignupReqDto;
import com.example.mandarin_shop_back.entity.product.OptionName;
import com.example.mandarin_shop_back.entity.user.User;
import com.example.mandarin_shop_back.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean userExists(int userId) {
        // userMapper를 사용하여 userId가 존재하는지 확인
        User user = userMapper.findUserByUserId(userId);
        return user != null;
    }

}
