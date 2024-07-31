package com.example.mandarin_shop_back.controller.advice;

import com.example.mandarin_shop_back.exception.DeleteException;
import com.example.mandarin_shop_back.exception.RequestInLimitTimeException;
import com.example.mandarin_shop_back.exception.SaveException;
import com.example.mandarin_shop_back.exception.ValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    // internalServerError 500에러(해당 서버잘못)를 보냄
    @ExceptionHandler(SaveException.class)
    public ResponseEntity<?> saveException(SaveException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    // badRequest 400에러(클라이언트 잘못)를 보냄
    @ExceptionHandler(ValidException.class)
    public ResponseEntity<?> validException(ValidException e) {
        return ResponseEntity.badRequest().body(e.getErrorMap());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFoundException(UsernameNotFoundException e) {
        System.out.println("Handling UsernameNotFoundException");
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<?> deleteException(DeleteException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(RequestInLimitTimeException.class)
    public ResponseEntity<?> RequestInLimitTimeException(RequestInLimitTimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
