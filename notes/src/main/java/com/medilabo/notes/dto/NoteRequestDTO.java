package com.medilabo.notes.dto;

import com.medilabo.notes.model.Note;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


/**
 * Validated payload for NoteRequest
 * <p>
 *
 * @param patId patient id
 * @param patient patient's name
 * @param note medical information
 */
public record NoteRequestDTO(
        @NotNull(message = "patId must not be null")
        Long patId,
        @NotBlank(message = "Patient is mandatory")
        String patient,
        @NotBlank(message = "Note is mandatory")
        String note
) {
    public Note toNote() {
        Note noteEntity = new Note();
        noteEntity.setPatId(patId);
        noteEntity.setPatient(patient);
        noteEntity.setNote(note);
        return noteEntity;
    }
}
