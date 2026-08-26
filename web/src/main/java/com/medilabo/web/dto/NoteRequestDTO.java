package com.medilabo.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NoteRequestDTO(
        @NotNull(message = "patId must not be null")
        Long patId,
        @NotBlank(message = "Patient is mandatory")
        String patient,
        @NotBlank(message = "Note is mandatory")
        String note
) {
}
