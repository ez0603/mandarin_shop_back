package com.example.mandarin_shop_back.security;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Builder
public class PrincipalAdmin implements PrincipalDetails {

    private final Integer adminId;
    private final String adminName;
    private final String email;
    private final Integer roleId;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Integer getId() {
        return adminId;
    }

    @Override
    public String getName() {
        return adminName;
    }

    @Override
    public Integer getRoleId() {
        return roleId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // Not needed for this example
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
