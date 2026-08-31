package com.medilabo.auth.security;

import com.medilabo.auth.model.User;
import com.medilabo.auth.repository.UserRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Loads a {@link User} for Spring Security's authentification
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * @param identifier the login identifier
     * @return {@link CustomUserDetails}
     * @throws UsernameNotFoundException if no user has this identifier
     */
    @Override
    @Nonnull
    public UserDetails loadUserByUsername(@Nonnull String identifier) throws UsernameNotFoundException {
        User user = userRepository.findByIdentifier(identifier).orElseThrow(() -> new UsernameNotFoundException(identifier));
        return new CustomUserDetails(user);
    }
}
