package com.example.mandarin_shop_back.security;

import com.example.mandarin_shop_back.entity.account.Admin;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PrincipalAdmin implements UserDetails {
    private int adminId;
    private int roleId;
    private String name;
    private String adminName;
    private String adminPassword;
    private String email;
    private List<String> roles;

    public PrincipalAdmin(Admin admin) {
        this.adminId = admin.getAdminId();
        this.roleId = admin.getRoleId();
        this.name = admin.getName();
        this.adminName = admin.getAdminName();
        this.adminPassword = admin.getAdminPassword();
        this.email = admin.getEmail();
        this.roles = admin.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public PrincipalAdmin(int adminId, int roleId, String name, String adminName, String adminPassword, String email, List<String> roles) {
        this.adminId = adminId;
        this.roleId = roleId;
        this.name = name;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return adminPassword;
    }

    @Override
    public String getUsername() {
        return adminName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
