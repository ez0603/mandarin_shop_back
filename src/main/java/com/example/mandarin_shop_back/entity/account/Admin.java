package com.example.mandarin_shop_back.entity.account;

import com.example.mandarin_shop_back.security.PrincipalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    private int adminId;
    private String name;
    private String adminName;
    private String adminPassword;
    private String email;
    private LocalDate createDate;
    private LocalDate updateDate;

    public PrincipalUser toPrincipalUser() {
        return PrincipalUser.builder()
                .adminId(adminId)
                .name(name)
                .adminName(adminName)
                .email(email)
                .build();
    }
}
