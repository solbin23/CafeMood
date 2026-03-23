package com.cafe.cafeMood.common.auth;

import com.cafe.cafeMood.user.domain.UserRole;
import com.cafe.cafeMood.user.dto.request.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtAuthFilter extends OncePerRequestFilter {

    public static final String LOGIN_USER_ATTRIBUTE = "loginUser";

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (jwtTokenProvider.validateToken(token)) {
                LoginUser loginUser = jwtTokenProvider.getLoginUser(token);
                request.setAttribute(LOGIN_USER_ATTRIBUTE, loginUser);
            }
        }

        filterChain.doFilter(request, response);
    }

}
