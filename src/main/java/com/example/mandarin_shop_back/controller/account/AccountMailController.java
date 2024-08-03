package com.example.mandarin_shop_back.controller.account;

import com.example.mandarin_shop_back.aop.annotation.ParamsPrintAspect;
import com.example.mandarin_shop_back.dto.account.request.UserFindPasswordReqDto;
import com.example.mandarin_shop_back.dto.account.request.UserSearchUserNameReqDto;
import com.example.mandarin_shop_back.dto.account.request.verifyAuthCodeReqDto;
import com.example.mandarin_shop_back.entity.user.User;
import com.example.mandarin_shop_back.service.admin.AccountMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/mail")
public class AccountMailController {

    @Autowired
    AccountMailService accountMailService;

    // 메일에 인증번호 보내기
    @PostMapping("/{email}/send/authenticate")
    @ResponseBody
    public ResponseEntity<?> sendAuthenticate(HttpServletRequest request, @PathVariable String email) {
        request.getSession().setAttribute("timer", new Date());
        return ResponseEntity.ok(accountMailService.sendAuthMail(email));
    }

    // 인증번호가 유효한지
    @PostMapping("/verify/authenticate")
    public ResponseEntity<?> verifyAuthCode(@RequestBody verifyAuthCodeReqDto verifyAuthCodeReqDto) {
        Map<String, String> resultMap = accountMailService.verifyEmailCode(verifyAuthCodeReqDto.getEmail(), verifyAuthCodeReqDto.getAuthCode());
        String status = resultMap.get("status");
        String message = resultMap.get("message");

        if("success".equals(status)) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }


    // 아이디 찾기
    @PostMapping("/send/id")
    public ResponseEntity<?> send(HttpServletRequest request, @RequestBody UserSearchUserNameReqDto userSearchUserNameReqDto) {
        request.getSession().setAttribute("timer", new Date());
        User user = accountMailService.searchAccountByNameAndEmail(userSearchUserNameReqDto.getUsername(), userSearchUserNameReqDto.getEmail());
        if(user == null){
            throw new UsernameNotFoundException("일치하는 회원정보가 없습니다.");
        }
        accountMailService.searchAccountByMail(user);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // 비밀번호 찾기
    @ParamsPrintAspect
    @PostMapping("/send/temporary/password")
    public ResponseEntity<?> sendTemporaryPassword(HttpServletRequest request, @RequestBody UserFindPasswordReqDto adminFindPasswordReqDto) {
        request.getSession().setAttribute("timer", new Date());
        User user = accountMailService.searchAccountByUsernameAndEmail(adminFindPasswordReqDto.getUsername(), adminFindPasswordReqDto.getEmail());
        if(user == null) {
            throw new UsernameNotFoundException("일치하는 회원정보가 없습니다.");
        }

        if (user != null && accountMailService.sendTemporaryPassword(user)) {
            return ResponseEntity.ok("임시 비밀번호가 성공적으로 전송되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("임시 비밀번호 전송에 실패했습니다.");
        }
    }
}