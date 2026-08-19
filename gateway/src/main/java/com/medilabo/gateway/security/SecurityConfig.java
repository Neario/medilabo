package com.medilabo.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtWebFilter jwtWebFilter) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.GET, "/patients", "/patients/**").hasAnyRole("ORGANIZER", "PRACTITIONER")
                        .pathMatchers(HttpMethod.POST, "/patients").hasRole("ORGANIZER")
                        .pathMatchers(HttpMethod.PUT, "/patients/**").hasRole("ORGANIZER")
                        .pathMatchers(HttpMethod.DELETE, "/patients/**").hasRole("ORGANIZER")
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }
}
