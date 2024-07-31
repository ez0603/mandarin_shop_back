package com.example.mandarin_shop_back.controller.user;


import com.example.mandarin_shop_back.aop.annotation.ParamsPrintAspect;
import com.example.mandarin_shop_back.aop.annotation.ValidAspect;
import com.example.mandarin_shop_back.dto.account.request.EditPasswordReqDto;
import com.example.mandarin_shop_back.security.PrincipalUser;
import com.example.mandarin_shop_back.service.admin.AdminAccountService;
import com.example.mandarin_shop_back.service.user.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account/user")
public class UserAccountController {

    @Autowired
    private AdminAccountService adminAccountService;
    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/principal")
    public ResponseEntity<?> getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        return ResponseEntity.ok(principalUser);
    }

    @ParamsPrintAspect
    @ValidAspect
    @PutMapping("/password")
    public ResponseEntity<?> editPassword(@Valid @RequestBody EditPasswordReqDto editPasswordReqDto, BindingResult bindingResult) {
        userAccountService.editPassword(editPasswordReqDto);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/id")
    public ResponseEntity<?> getUserName(@RequestParam(value = "customerName") String customerName, @RequestParam(value = "email")String email) {
        return ResponseEntity.ok(userAccountService.findAccountUserByNameAndEmail(customerName, email));
    }
}