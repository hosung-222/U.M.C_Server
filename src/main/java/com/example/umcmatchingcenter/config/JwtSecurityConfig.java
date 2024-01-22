package com.example.umcmatchingcenter.config;

import com.example.umcmatchingcenter.jwt.JwtExceptionFilter;
import com.example.umcmatchingcenter.jwt.JwtFilter;
import com.example.umcmatchingcenter.jwt.TokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;
    public JwtSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(new JwtFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class
        );
        http.addFilterBefore(new JwtExceptionFilter(),
                JwtFilter.class
        );
    }
}
