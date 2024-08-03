package com.example.mandarin_shop_back.dto.account.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFindPasswordReqDto {
    private String username;
    private String email;
}
