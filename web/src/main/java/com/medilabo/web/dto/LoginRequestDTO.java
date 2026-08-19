package com.medilabo.web.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank String identifier,
        @NotBlank String password
) {
}
