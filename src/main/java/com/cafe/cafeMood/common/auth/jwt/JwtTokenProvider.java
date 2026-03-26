package com.cafe.cafeMood.common.auth.jwt;


import com.cafe.cafeMood.common.auth.dto.LoginUser;
import com.cafe.cafeMood.user.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenValidityMillis;

    public JwtTokenProvider(String secret, long accessTokenValidityMillis) {

        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityMillis = accessTokenValidityMillis;
    }

    public String createAccessToken(Long userId, String email,UserRole role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenValidityMillis);

        return Jwts.builder().subject(String.valueOf(userId))
                .claim("email",email)
                .claim("role",role.name())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public LoginUser getLoginUser(String token) {
        Claims claims = parseClaims(token);

        Long userId = Long.valueOf(claims.getSubject());
        String email = claims.get("email", String.class);
        String roleValue = claims.get("role", String.class);
        UserRole role = UserRole.valueOf(roleValue);

        return new LoginUser(userId,email,role);
    }

    public UserRole getRole(String token) {
        String role = parseClaims(token).get("role", String.class);
        return UserRole.valueOf(role);
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
