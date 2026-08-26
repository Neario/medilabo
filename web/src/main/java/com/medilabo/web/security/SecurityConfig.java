package com.medilabo.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security config for web page with {@link JwtCookieAuthenticationFilter}
 * access rules bases on role
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtCookieAuthenticationFilter jwtCookieAuthenticationFilter;

    /**
     * Defines the URL authorization rules     *
     * @param http the security configuration builder
     * @return the built filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/css/**", "/error").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/patients").hasAnyRole("ORGANIZER", "PRACTITIONER")
                .requestMatchers("/patients/create").hasRole("ORGANIZER")
                .requestMatchers("/patients/{id}/edit").hasRole("ORGANIZER")
                .requestMatchers(HttpMethod.GET, "/patients/{id}").hasAnyRole("ORGANIZER", "PRACTITIONER")
                .requestMatchers("/patients/{id}/notes/new").hasRole("PRACTITIONER")
                .requestMatchers("/patients/{id}/notes").hasRole("PRACTITIONER")
                .requestMatchers("/patients/**").hasRole("ORGANIZER")
                .anyRequest().authenticated())
                .addFilterBefore(jwtCookieAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));
        return http.build();
    }

    /**
     * @return the password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
