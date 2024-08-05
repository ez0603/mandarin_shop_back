package com.example.mandarin_shop_back.config;

import com.example.mandarin_shop_back.jwt.JwtProvider;
import com.example.mandarin_shop_back.security.exception.AuthEntryPoint;
import com.example.mandarin_shop_back.security.filter.JwtAuthenticationFilter;
import com.example.mandarin_shop_back.security.filter.MailSessionFilter;
import com.example.mandarin_shop_back.security.filter.PermitAllFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MailSessionFilter mailSessionFilter;

    @Autowired
    private PermitAllFilter permitAllFilter;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtProvider);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/server/**",
                        "/product/**",
                        "/order/**",
                        "/auth/**",
                        "/mail/**",
                        "/send/**",
                        "/account/**",
                        "/inventory/**",
                        "/public/**",
                        "/cart/**",
                        "/admin/auth/signin",
                        "/admin/signup",
                        "/user/signin",
                        "/user/signup")
                .permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(permitAllFilter, LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(mailSessionFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint);
    }
}
