package com.medilabo.auth.controller;

import com.medilabo.auth.dto.LoginRequest;
import com.medilabo.auth.dto.LoginResponse;
import com.medilabo.auth.service.interfaces.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing the login endpoint.
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Authenticates a user.
     *
     * @param loginRequest valid credentials
     * @return {@code 200 OK} with TOKEN JWT
     * @throws com.medilabo.auth.exception.UnauthorizedException if the credentials are not valid
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.identifier(), loginRequest.password());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
