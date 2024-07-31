package com.example.mandarin_shop_back.entity.account;

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

    private List<AdminRoleRegister> adminRoleRegisters;

    public PrincipalUser toPrincipalUser() {
        return PrincipalUser.builder()
                .adminId(adminId)
                .name(name)
                .adminName(adminName)
                .email(email)
                .build();
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // adminRoleRegisters가 null일 수 있으므로 체크
        if (adminRoleRegisters != null) {
            for (AdminRoleRegister adminRoleRegister : adminRoleRegisters) {
                // adminRoleRegister나 role, roleName이 null이 아닌지 체크
                if (adminRoleRegister != null && adminRoleRegister.getRole() != null && adminRoleRegister.getRole().getRoleName() != null) {
                    authorities.add(new SimpleGrantedAuthority(adminRoleRegister.getRole().getRoleName()));
                } else {
                    // 필요한 경우 null인 경우에 대한 로깅 또는 디폴트 처리
                    // System.out.println("Invalid role or role name detected in adminRoleRegister");
                }
            }
        } else {
            // 필요한 경우 adminRoleRegisters가 null인 경우에 대한 로깅 또는 디폴트 처리
            // System.out.println("adminRoleRegisters is null");
        }

        return authorities;
    }

}
