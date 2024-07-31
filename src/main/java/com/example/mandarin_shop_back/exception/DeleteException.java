package com.example.mandarin_shop_back.exception;

import lombok.Getter;

import java.util.Map;

public class DeleteException extends RuntimeException{

    @Getter
    Map<String, String> errorMap;

    public DeleteException(Map<String, String> errorMap) {
        super("데이터 삭제 오류");
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }

}
