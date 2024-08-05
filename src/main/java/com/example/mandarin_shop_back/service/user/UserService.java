package com.example.mandarin_shop_back.service.user;

import com.example.mandarin_shop_back.entity.user.User;
import com.example.mandarin_shop_back.repository.UserMapper;
import com.example.mandarin_shop_back.security.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExists(int userId) {
        User user = userMapper.findUserByUserId(userId);
        return user != null;
    }
}
