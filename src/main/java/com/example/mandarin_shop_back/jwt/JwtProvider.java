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
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.adminMapper = adminMapper;
        this.userMapper = userMapper;
    }

    public String generateToken(Admin admin) { // JWT 생성
        int adminId = admin.getAdminId();
        String adminName = admin.getAdminName();
        Date expireDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 20));

        String accessToken = Jwts.builder()
                .claim("adminId", adminId) // 제이슨 형식으로 키밸류 들어감
                .claim("adminName", adminName)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return accessToken;
    }

    public String generateUserToken(User user) { // JWT 생성
        int userId = user.getUserId();
        String username = user.getUsername();
        Date expireDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 20));

        String accessToken = Jwts.builder()
                .claim("userId", userId) // 제이슨 형식으로 키밸류 들어감
                .claim("username", username)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return accessToken;
    }

    public String removeBearer(String token) { // Bearer 접두사 제거
        if (!StringUtils.hasText(token)) {
            return null;
        }
        return token.substring("Bearer ".length());
    }

    public Claims getClaims(String token) { // 토큰에서 클레임 추출
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token) // 토큰을 클래임으로 변환하는 작업
                    .getBody();
        } catch (Exception e) {
            log.error("JWT 인증 오류: {}", e.getMessage());
            return null;
        }

        return claims;
    }

    public Authentication getAuthentication(Claims claims) { // 사용자 인증
        String adminName = claims.get("adminName").toString();
        Admin admin = adminMapper.findAdminByUsername(adminName);
        if (admin == null) {
            return null;
        }

        PrincipalUser principalUser = admin.toPrincipalUser();
        return new UsernamePasswordAuthenticationToken(principalUser, principalUser.getPassword(), principalUser.getAuthorities());
    }

    public String generateAuthMailToken(String toMailAddress) { // 이메일 인증 토큰 생성
        Date expireDate = new Date(new Date().getTime() + (1000 * 60 * 5));
        return Jwts.builder()
                .claim("toMailAddress", toMailAddress)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
