package com.example.mandarin_shop_back.config;

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
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/server/**", "/admin/**",
                        "/product/**", "/order/**", "/user/**", "/auth/**",
                        "/mail/**", "/send/**", "/account/**", "/inventory/**", "/public/**", "/cart/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(permitAllFilter, LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(mailSessionFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint);
    }
}
