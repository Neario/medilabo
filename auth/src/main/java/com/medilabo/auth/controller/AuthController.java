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

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.identifier(), loginRequest.password());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
