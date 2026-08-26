package com.medilabo.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Payload for creating a note on a patient
 *
 * @param patId identifier of the patient the note
 * @param patient name of the patient
 * @param note the note's content
 */
public record NoteRequestDTO(
        @NotNull(message = "patId must not be null")
        Long patId,
        @NotBlank(message = "Patient is mandatory")
        String patient,
        @NotBlank(message = "Note is mandatory")
        String note
) {
}
