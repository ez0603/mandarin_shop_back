package com.example.mandarin_shop_back.dto.account.request;


import lombok.Data;

@Data
public class UserSearchUserNameReqDto {
    private String username;
    private String email;
}
