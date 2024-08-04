package com.example.mandarin_shop_back.entity.user;

import com.example.mandarin_shop_back.security.PrincipalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public PrincipalUser toPrincipalUser() {
        return new PrincipalUser(this);
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        if (userRoleRegisters == null) {
            return Collections.emptyList();
        }

        return userRoleRegisters.stream()
                .filter(userRoleRegister -> userRoleRegister.getRole() != null && userRoleRegister.getRole().getRoleName() != null)
                .map(userRoleRegister -> new SimpleGrantedAuthority(userRoleRegister.getRole().getRoleName()))
                .collect(Collectors.toList());
    }
}
