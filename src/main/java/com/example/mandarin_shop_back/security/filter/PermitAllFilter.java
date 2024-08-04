package com.example.mandarin_shop_back.security.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component
public class PermitAllFilter extends GenericFilter {

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/auth",
            "/user",
            "/mail",
            "/account",
            "/product"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();

        boolean isPermitAll = EXCLUDED_PATHS.stream().anyMatch(uri::startsWith);
        request.setAttribute("isPermitAll", isPermitAll);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
