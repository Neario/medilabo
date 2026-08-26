package com.medilabo.web.client;

import com.medilabo.web.dto.LoginRequestDTO;
import com.medilabo.web.dto.LoginResponseDTO;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

/**
 * HTTP client for the Auth login endpoint
 */
public interface AuthClient {

    /**
     * Authenticates a user.
     *
     * @param loginRequestDTO credential identifier
     * @return String Token JWT
     * @throws org.springframework.web.client.HttpClientErrorException.Unauthorized if the credentials are invalid
     */
    @PostExchange("/auth/login")
    LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO);
}
