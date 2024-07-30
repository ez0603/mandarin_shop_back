package com.example.mandarin_shop_back.security;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class PrincipalUser implements UserDetails {
    private int adminId;
    private String name;
    private String adminName;
    private String email;
    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // roles 리스트를 통해 GrantedAuthority 객체 리스트를 생성합니다.
        // 예시: "ROLE_USER", "ROLE_ADMIN" 같은 권한이 roles에 포함되어 있다고 가정합니다.
        if (roles == null) {
            return Collections.emptyList();
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return "";  // 비밀번호를 저장하지 않거나 다른 방식으로 처리한다면 빈 문자열로 둘 수 있습니다.
    }

    @Override
    public String getUsername() {
        return adminName;  // 사용자 식별자로 사용할 필드
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
