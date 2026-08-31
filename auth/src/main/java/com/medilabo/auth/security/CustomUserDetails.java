package com.medilabo.auth.security;

import com.medilabo.auth.model.User;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Adapter for get a {@link UserDetails} with a {@link User} entity
 * Logic Spring Security's authentication
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;

    /**
     * @return a single authority derived from the user's role, prefixed with {@code ROLE_}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    /**
     * @return the user's hashed password
     */
    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    /**
     * @return the user's login identifier
     */
    @Override
    public String getUsername() {
        return user.getIdentifier();
    }
}
