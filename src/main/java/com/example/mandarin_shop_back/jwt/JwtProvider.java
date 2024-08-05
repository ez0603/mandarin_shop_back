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
import org.springframework.stereotype.Component;

import java.security.Key;
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

    public String generateUserToken(User user) {
        int userId = user.getUserId();
        String username = user.getUsername();
        Date expireDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 20));

        if (username == null) {
            log.error("Username is null for user: " + userId);
            throw new IllegalArgumentException("Username cannot be null");
        }

        String token = Jwts.builder()
                .claim("userId", userId)
                .claim("username", username)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        log.info("Generated user token for user: {}", username);
        return token;
    }

    public String generateAdminToken(Admin admin) {
        int adminId = admin.getAdminId();
        String adminName = admin.getAdminName();
        Date expireDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 20));

        if (adminName == null) {
            log.error("Admin name is null for admin: " + adminId);
            throw new IllegalArgumentException("Admin name cannot be null");
        }

        String token = Jwts.builder()
                .claim("adminId", adminId)
                .claim("adminName", adminName)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        log.info("Generated admin token for admin: {}", adminName);
        return token;
    }

    public Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("JWT 인증 오류: {}", e.getMessage());
        }
        return claims;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("JWT 유효성 검사 실패: {}", e.getMessage());
            return false;
        }
    }

    public Authentication getAuthentication(Claims claims) {
        if (claims == null) {
            log.error("Claims are null.");
            return null;
        }

        String username = claims.get("username", String.class);
        String adminName = claims.get("adminName", String.class);

        log.info("Extracted from claims - username: {}, adminName: {}", username, adminName);

        if (adminName != null) {
            Admin admin = adminMapper.findAdminByUsername(adminName);
            if (admin == null) {
                log.error("No admin found with username: " + adminName);
                return null;
            }
            PrincipalAdmin principalAdmin = admin.toPrincipalAdmin();
            return new UsernamePasswordAuthenticationToken(principalAdmin, null, principalAdmin.getAuthorities());
        } else if (username != null) {
            User user = userMapper.findUserByUsername(username);
            if (user == null) {
                log.error("No user found with username: " + username);
                return null;
            }
            PrincipalUser principalUser = user.toPrincipalUser();
            return new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
        } else {
            log.error("Both username and adminName are null in claims.");
            return null;
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
