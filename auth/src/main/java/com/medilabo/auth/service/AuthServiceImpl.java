package com.medilabo.auth.service;

import com.medilabo.auth.exception.UnauthorizedException;
import com.medilabo.auth.model.User;
import com.medilabo.auth.repository.UserRepository;
import com.medilabo.auth.security.JwtService;
import com.medilabo.auth.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * {@link AuthService} implementation.
 * <p>
 * Delegates credential verification to Spring Security's
 * {@link AuthenticationManager} (backed by {@code CustomUserDetailsService}),
 * then issues a JWT via {@link JwtService} on success.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    /** {@inheritDoc} */
    @Override
    public String login(String identifier, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(identifier, password)
            );
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Invalid email or password");
        }
        User user = userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new UsernameNotFoundException(identifier));
        return jwtService.generateAccessToken(user);
    }
}
