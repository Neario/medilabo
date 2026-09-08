package com.medilabo.risk.client;

import com.medilabo.risk.dto.NoteResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

/**
 * HTTP client for the {@code notes} endpoints
 */
public interface NoteClient {

    /**
     * Lists all notes for a patient.
     *
     * @param patId identifier of the patient
     * @return all notes for this patient
     */
    @GetExchange("/notes/{patId}")
    List<NoteResponseDTO> getNotes(@PathVariable long patId);
}
