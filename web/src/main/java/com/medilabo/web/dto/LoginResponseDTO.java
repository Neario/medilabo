package com.medilabo.web.dto;

/**
 * JWT TOKEN when success login
 * @param token JWT TOKEN
 */
public record LoginResponseDTO(
        String token
) {
}
