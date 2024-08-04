package com.example.mandarin_shop_back.security;

import com.example.mandarin_shop_back.entity.user.User;
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
public class PrincipalUser implements UserDetails {
    private int userId;
    private int roleId;
    private String username;
    private String email;
    private String password;
    private List<String> roles;

    public PrincipalUser(User user) {
        this.userId = user.getUserId();
        this.roleId = user.getRoleId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public PrincipalUser(int userId, int roleId, String username, String email, String password, List<String> roles) {
        this.userId = userId;
        this.roleId = roleId;
        this.username = username;
        this.email = email;
        this.password = password;
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
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
