package com.example.mandarin_shop_back.exception;

public class RequestInLimitTimeException extends RuntimeException {
    public RequestInLimitTimeException(long leftTIme) {
        super("다음요청까지 남은시간: " + leftTIme);
    }
}
