package com.medilabo.web.client;

import com.medilabo.web.dto.LoginRequestDTO;
import com.medilabo.web.dto.LoginResponseDTO;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface AuthClient {
    @PostExchange("/auth/login")
    LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO);
}
