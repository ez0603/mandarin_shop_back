package com.example.mandarin_shop_back.controller.user;


import com.example.mandarin_shop_back.aop.annotation.ParamsPrintAspect;
import com.example.mandarin_shop_back.aop.annotation.ValidAspect;
import com.example.mandarin_shop_back.dto.account.request.EditPasswordReqDto;
import com.example.mandarin_shop_back.security.PrincipalDetails;
import com.example.mandarin_shop_back.security.PrincipalUser;
import com.example.mandarin_shop_back.service.user.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private UserAccountService userAccountService;

    @GetMapping("/principal")
    public PrincipalDetails getUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof PrincipalDetails) {
            return (PrincipalDetails) principal;
        } else {
            throw new IllegalStateException("Authenticated principal is not a user");
        }
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