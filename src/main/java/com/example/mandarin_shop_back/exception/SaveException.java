package com.example.mandarin_shop_back.exception;

import lombok.Getter;

import java.util.Map;

public class SaveException extends RuntimeException{

    @Getter
    Map<String, String> errorMap;
    public SaveException() {
        super("데이터 저장 오류.");
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
