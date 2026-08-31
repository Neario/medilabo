package com.medilabo.auth.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Validated login payload.
 *
 * @param identifier user's login identifier
 * @param password user's password
 */
public record LoginRequest(
        @NotBlank String identifier,
        @NotBlank String password
) {
}
