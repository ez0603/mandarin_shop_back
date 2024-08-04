package com.example.mandarin_shop_back.controller.user;

import com.example.mandarin_shop_back.dto.user.request.UserSignupReqDto;
import com.example.mandarin_shop_back.service.admin.AccountMailService;
import com.example.mandarin_shop_back.service.user.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private AccountMailService accountMailService;

    @PostMapping("/signup")
    public ResponseEntity<?> userSignup(@Valid @RequestBody UserSignupReqDto userSignupReqDto, BindingResult bindingResult) {
        userAuthService.signup(userSignupReqDto);
        return ResponseEntity.created(null).body(true);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> userSignin(@RequestBody UserSignupReqDto userSignupReqDto) {
        try {
            String token = userAuthService.userSignin(userSignupReqDto);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/signup/request")
    public ResponseEntity<?> requestSignup(@Valid @RequestBody UserSignupReqDto userSignupReqDto, BindingResult bindingResult) {
        accountMailService.sendAuthMail(userSignupReqDto.getEmail());
        return ResponseEntity.ok("Verification email sent");
    }

    @PostMapping("/signup/verify")
    public ResponseEntity<?> verifySignup(@Valid @RequestBody UserSignupReqDto userSignupReqDto, BindingResult bindingResult) {
        Map<String, String> resultMap = accountMailService.verifyEmailCode(userSignupReqDto.getEmail(), userSignupReqDto.getAuthCode());
        String status = resultMap.get("status");
        String message = resultMap.get("message");

        if ("success".equals(status)) {
            userAuthService.signup(userSignupReqDto);
            return ResponseEntity.ok("User signed up successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
}
