package com.example.mandarin_shop_back.controller.account;


import com.example.mandarin_shop_back.aop.annotation.ParamsPrintAspect;
import com.example.mandarin_shop_back.aop.annotation.ValidAspect;
import com.example.mandarin_shop_back.dto.account.request.EditPasswordReqDto;
import com.example.mandarin_shop_back.security.PrincipalAdmin;
import com.example.mandarin_shop_back.service.admin.AdminAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AdminAccountController {

    @Autowired
    private AdminAccountService adminAccountService;

    @GetMapping("/principal")
    public PrincipalAdmin getAdminPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof PrincipalAdmin) {
            return (PrincipalAdmin) authentication.getPrincipal();
        } else {
            throw new IllegalStateException("No authenticated admin found");
        }
    }

    @ParamsPrintAspect
    @ValidAspect
    @PutMapping("/password")
    public ResponseEntity<?> editPassword(@Valid @RequestBody EditPasswordReqDto editPasswordReqDto, BindingResult bindingResult) {
        adminAccountService.editPassword(editPasswordReqDto);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/id")
    public ResponseEntity<?> getAdminName(@RequestParam(value = "name") String name, @RequestParam(value = "email")String email) {
        return ResponseEntity.ok(adminAccountService.findAccountByNameAndEmail(name, email));
    }


}