package com.example.mandarin_shop_back.jwt;

import com.example.mandarin_shop_back.entity.account.Admin;
import com.example.mandarin_shop_back.entity.user.User;
import com.example.mandarin_shop_back.repository.AdminMapper;
import com.example.mandarin_shop_back.repository.UserMapper;
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
import org.springframework.util.StringUtils;

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

    public String generateToken(Admin admin) { // JWT 생성
        int adminId = admin.getAdminId();
        String adminName = admin.getAdminName();
        Date expireDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 20));

        return Jwts.builder()
                .claim("adminId", adminId)
                .claim("adminName", adminName)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateUserToken(User user) { // JWT 생성
        int userId = user.getUserId();
        String username = user.getUsername();
        Date expireDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 20));

        return Jwts.builder()
                .claim("userId", userId)
                .claim("username", username)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String removeBearer(String token) { // Bearer 접두사 제거
        if (!StringUtils.hasText(token)) {
            return null;
        }
        return token.substring("Bearer ".length());
    }

    public Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.info("Claims extracted: {}", claims);
        } catch (Exception e) {
            log.error("JWT 인증 오류: {}", e.getMessage());
            return null;
        }
        return claims;
    }

    public Authentication getAuthentication(Claims claims) {
        if (claims == null) {
            log.error("Claims are null.");
            return null;
        }

        if (claims.containsKey("adminName")) {
            String adminName = claims.get("adminName").toString();
            log.info("Admin name from token: {}", adminName);
            Admin admin = adminMapper.findAdminByUsername(adminName);
            if (admin == null) {
                log.error("No admin found with username: " + adminName);
                return null;
            }

            PrincipalUser principalUser = admin.toPrincipalUser();
            return new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
        } else if (claims.containsKey("username")) {
            String username = claims.get("username").toString();
            log.info("User name from token: {}", username);
            User user = userMapper.findUserByUsername(username);
            if (user == null) {
                log.error("No user found with username: " + username);
                return null;
            }

            PrincipalUser principalUser = user.toPrincipalUser(); // Assuming a similar method exists for User
            return new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
        }

        log.error("Token does not contain necessary claims for authentication. Claims: {}", claims);
        return null;
    }

    public String generateAuthMailToken(String toMailAddress) { // 이메일 인증 토큰 생성
        Date expireDate = new Date(System.currentTimeMillis() + (1000 * 60 * 5));
        return Jwts.builder()
                .claim("toMailAddress", toMailAddress)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
