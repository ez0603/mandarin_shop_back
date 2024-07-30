package com.example.mandarin_shop_back.entity.user;

import com.example.mandarin_shop_back.security.PrincipalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private int userId;
    private int roleId;
    private String roleNameKor;
    private String customerName;
    private String username;
    private String password;
    private String email;
    private LocalDate createDate;
    private LocalDate updateDate;

    private List<UserRoleRegister> userRoleRegisters;

    public PrincipalUser toPrincipalUser() {
        List<String> roles = (roleNameKor != null) ? List.of(roleNameKor) : Collections.emptyList();

        return PrincipalUser.builder()
                .adminId(this.userId)
                .adminName(this.username)
                .email(this.email)
                .roles(roles)
                .build();
    }
}
