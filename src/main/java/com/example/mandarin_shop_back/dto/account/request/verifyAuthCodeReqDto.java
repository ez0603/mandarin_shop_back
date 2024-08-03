package com.example.mandarin_shop_back.dto.account.request;

import lombok.Data;

@Data
public class verifyAuthCodeReqDto {
    private String email;
    private String authCode;
}
