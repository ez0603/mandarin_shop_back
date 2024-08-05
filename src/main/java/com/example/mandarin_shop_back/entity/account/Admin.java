package com.example.mandarin_shop_back.entity.account;

import com.example.mandarin_shop_back.entity.user.UserRoleRegister;
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
        return PrincipalAdmin.builder()
                .adminId(adminId)
                .adminName(adminName)
                .email(email)
                .authorities(getAuthorities())
                .build();
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (adminRoleRegisters != null) {
            for (AdminRoleRegister adminRoleRegister : adminRoleRegisters) {
                authorities.add(new SimpleGrantedAuthority(adminRoleRegister.getRole().getRoleName()));
            }
        }
        return authorities;
    }
}
