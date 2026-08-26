package com.medilabo.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Validated login form payload
 *
 * @param identifier user's login identifier
 * @param password user's password
 */
public record LoginRequestDTO(
        @NotBlank String identifier,
        @NotBlank String password
) {
}
