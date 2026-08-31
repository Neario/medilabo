package com.medilabo.auth.dto;

/**
 * Response returned on successful login.
 *
 * @param token the TOKEN JWT
 */
public record LoginResponse(
        String token
) {
}
