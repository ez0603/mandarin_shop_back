package com.example.mandarin_shop_back.entity.user;

import com.example.mandarin_shop_back.security.PrincipalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private String phone;
    private String email;
    private String address;
    private LocalDate createDate;
    private LocalDate updateDate;

    private List<UserRoleRegister> userRoleRegisters;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (userRoleRegisters != null) {
            for (UserRoleRegister userRoleRegister : userRoleRegisters) {
                authorities.add(new SimpleGrantedAuthority(userRoleRegister.getRole().getRoleName()));
            }
        }
        return authorities;
    }

    public PrincipalUser toPrincipalUser() {
        return PrincipalUser.builder()
                .userId(userId)
                .username(username)
                .email(email)
                .authorities(getAuthorities())
                .build();
    }
}
