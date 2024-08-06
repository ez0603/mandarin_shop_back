package com.example.mandarin_shop_back.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface PrincipalDetails extends UserDetails {
    Integer getId();
    String getName();
    Integer getRoleId();
}
