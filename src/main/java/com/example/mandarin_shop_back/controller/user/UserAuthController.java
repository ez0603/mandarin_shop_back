package com.example.mandarin_shop_back.controller.user;

import com.example.mandarin_shop_back.dto.account.request.AdminSignupReqDto;
import com.example.mandarin_shop_back.dto.user.request.UserSignupReqDto;
import com.example.mandarin_shop_back.entity.user.User;
import com.example.mandarin_shop_back.repository.UserMapper;
import com.example.mandarin_shop_back.service.user.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/signup") // 전화번호 가입
    public ResponseEntity<?> userSignup(@Valid @RequestBody UserSignupReqDto userSignupReqDto, BindingResult bindingResult) {
        System.out.println("유저 로그인 들어옴??");
        userAuthService.signup(userSignupReqDto);

        return ResponseEntity.created(null).body(true);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> userSignin(@RequestBody UserSignupReqDto userSignupReqDto) {
        System.out.println(123);
        return ResponseEntity.ok(userAuthService.userSignin(userSignupReqDto));
    }
}
