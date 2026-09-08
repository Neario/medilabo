package com.medilabo.risk.dto;

/**
 * Note data as returned by the {@code notes} service
 *
 * @param note the note's content
 */
public record NoteResponseDTO(
        String note
) {
}
