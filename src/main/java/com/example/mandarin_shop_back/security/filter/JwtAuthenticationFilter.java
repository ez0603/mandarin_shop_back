package com.example.mandarin_shop_back.security.filter;

import com.example.mandarin_shop_back.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Boolean isPermitAll = (Boolean) request.getAttribute("isPermitAll");

        logger.info("Request URI: " + request.getRequestURI() + " | isPermitAll: " + isPermitAll);

        if (isPermitAll != null && isPermitAll) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = request.getHeader("Authorization");

        logger.info("Authorization header: " + accessToken);

        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            String removedBearerToken = jwtProvider.removeBearer(accessToken);
            Claims claims;

            try {
                claims = jwtProvider.getClaims(removedBearerToken);
                if (claims == null) {
                    throw new Exception("Invalid JWT token");
                }
            } catch (Exception e) {
                logger.severe("JWT token is not valid: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is not valid"); // 인증실패 (401 에러)
                return;
            }

            Authentication authentication = jwtProvider.getAuthentication(claims);

            if (authentication == null) {
                logger.severe("Authentication failed for claims: " + claims);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is not valid"); // 인증실패
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            logger.severe("Authorization header is missing or invalid: " + accessToken);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is missing or invalid"); // 인증실패
            return;
        }

        filterChain.doFilter(request, response);
    }
}
