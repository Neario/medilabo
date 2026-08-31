package com.medilabo.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * {@link WebFilter} that authenticates every request from its {@code Authorization: Bearer} JWT.
 * <p>
 * handling every call and re-validated token for {@code patient}, {@code notes} or {@code auth}
 */
@Component
public class JwtWebFilter  implements WebFilter {
    private final SecretKey key;

    /**
     * @param secret HMAC signing secret, shared with {@code auth} and {@code web}
     */
    public JwtWebFilter(@Value("${jwt.secretKey}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Verify the request's JWT, if present and publishes in {@link Authentication} into the security context
     * Any missing or invalid token , the request is unauthenticated in {@link SecurityConfig}
     *
     * @param exchange the current request/response exchange
     * @param chain the remaining filter chain
     * @return WebFilterChain
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String token = getToken(exchange.getRequest());
        try {
            Claims claims = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token)
                    .getPayload();

            String identifier = claims.get("identifier", String.class);
            String role = claims.get("role", String.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    identifier, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));

            return chain.filter(exchange).contextWrite(
                    ReactiveSecurityContextHolder.withSecurityContext(Mono.just(new SecurityContextImpl(authentication))));

        } catch (Exception e) {
            return chain.filter(exchange);
        }
    }

    private String getToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
