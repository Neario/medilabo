package com.medilabo.web.dto;

/**
 * Note data
 *
 * @param id MongoDB document id
 * @param patId identifier of the patient
 * @param patient name of the patient
 * @param note the note's content
 */
public record NoteResponseDTO(
        String id,
        Long patId,
        String patient,
        String note
) {
}
