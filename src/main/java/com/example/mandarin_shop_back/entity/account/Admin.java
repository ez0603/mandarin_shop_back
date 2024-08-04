package com.example.mandarin_shop_back.entity.account;

import com.example.mandarin_shop_back.security.PrincipalAdmin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private int adminId;
    private int roleId;
    private String name;
    private String adminName;
    private String adminPassword;
    private String email;
    private LocalDate createDate;
    private LocalDate updateDate;

    private List<AdminRoleRegister> adminRoleRegisters;

    public PrincipalAdmin toPrincipalAdmin() {
        return new PrincipalAdmin(this);
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        if (adminRoleRegisters == null) {
            return new ArrayList<>();
        }

        return adminRoleRegisters.stream()
                .filter(adminRoleRegister -> adminRoleRegister.getRole() != null && adminRoleRegister.getRole().getRoleName() != null)
                .map(adminRoleRegister -> new SimpleGrantedAuthority(adminRoleRegister.getRole().getRoleName()))
                .collect(Collectors.toList());
    }
}
