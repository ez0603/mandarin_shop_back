package com.example.mandarin_shop_back.jwt;

import com.example.mandarin_shop_back.entity.account.Admin;
import com.example.mandarin_shop_back.entity.user.User;
import com.example.mandarin_shop_back.repository.AdminMapper;
import com.example.mandarin_shop_back.repository.UserMapper;
import com.example.mandarin_shop_back.security.PrincipalAdmin;
import com.example.mandarin_shop_back.security.PrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Collection;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    private final Key key;
    private final AdminMapper adminMapper;
    private final UserMapper userMapper;

    @Autowired
    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            AdminMapper adminMapper,
            UserMapper userMapper) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.adminMapper = adminMapper;
        this.userMapper = userMapper;
    }

    public String generateToken(Admin admin) {
        return generateToken(admin.getAdminId(), admin.getAdminName(), admin.getAuthorities());
    }

    public String generateUserToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (1000 * 60 * 60 * 24 * 7)); // 7일 후 만료

        return Jwts.builder()
                .claim("userId", user.getUserId())
                .claim("username", user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    private String generateToken(int id, String username, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (1000 * 60 * 60 * 24 * 20)); // 20일 후 만료

        return Jwts.builder()
                .claim("id", id)
                .claim("username", username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String removeBearer(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        return token.substring("Bearer ".length());
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("JWT 인증 오류: {}", e.getMessage());
            return null;
        }
    }

    public Authentication getAuthentication(Claims claims) {
        if (claims == null) {
            log.error("Claims are null.");
            return null;
        }

        String username = claims.get("username", String.class);
        log.info("Username from token: {}", username);

        if (claims.containsKey("adminName")) {
            Admin admin = adminMapper.findAdminByUsername(username);
            if (admin == null) {
                log.error("No admin found with username: " + username);
                return null;
            }
            PrincipalAdmin principalAdmin = admin.toPrincipalAdmin();
            return new UsernamePasswordAuthenticationToken(principalAdmin, null, principalAdmin.getAuthorities());
        } else {
            User user = userMapper.findUserByUsername(username);
            if (user == null) {
                log.error("No user found with username: " + username);
                return null;
            }
            PrincipalUser principalUser = user.toPrincipalUser();
            return new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
        }
    }

    public String generateAuthMailToken(String toMailAddress) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (1000 * 60 * 5)); // 5분 후 만료

        return Jwts.builder()
                .claim("toMailAddress", toMailAddress)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


}
