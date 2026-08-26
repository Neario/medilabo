package com.medilabo.web.dto;

public record NoteResponseDTO(
        String id,
        Long patId,
        String patient,
        String note
) {
}
