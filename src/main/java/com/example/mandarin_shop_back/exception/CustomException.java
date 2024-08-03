package com.example.mandarin_shop_back.exception;


public class CustomException extends RuntimeException{

    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
