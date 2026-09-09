package com.medilabo.auth.security;

import com.medilabo.auth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

/**
 * Generate JWT TOKEN HS256, signed with the secret shared across {@code auth}, {@code web} and {@code gateway}
 */
@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate a signed JWT for an authenticated user with {@code identifier}, {@code role} and expiration after one hour
     *
     * @param user the authenticated user
     * @return the signed, compact JWT
     */
    public String generateAccessToken(User user) {
        return Jwts
                .builder()
                .subject(user.getId().toString())
                .claim("identifier", user.getIdentifier())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * @param token a signed JWT
     * @return the user id in the token's subject
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }
}
