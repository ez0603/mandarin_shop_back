package com.example.mandarin_shop_back.service.user;

import com.example.mandarin_shop_back.dto.user.request.UserSignupReqDto;
import com.example.mandarin_shop_back.entity.user.User;
import com.example.mandarin_shop_back.jwt.JwtProvider;
import com.example.mandarin_shop_back.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAuthService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;

    @Transactional(rollbackFor = Exception.class) // 회원가입
    public void signup(UserSignupReqDto userSignupReqDto) {
        User user = userSignupReqDto.toEntity();

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userSignupReqDto.getPassword());
        user.setPassword(encodedPassword);

        userMapper.saveUser(user);
        System.out.println("유저 서비스옴?");
    }

    public String userSignin(UserSignupReqDto userSignupReqDto) {
        User user = userMapper.findUserByUsername(userSignupReqDto.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("사용자 정보를 확인하세요");
        }
        if (!passwordEncoder.matches(userSignupReqDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("사용자 정보를 확인하세요");
        }
        return jwtProvider.generateUserToken(user);
    }
}
