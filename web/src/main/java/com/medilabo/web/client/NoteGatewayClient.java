package com.medilabo.web.client;

import com.medilabo.web.dto.NoteRequestDTO;
import com.medilabo.web.dto.NoteResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

/**
 * HTTP client for the {@code notes} endpoints
 */
public interface NoteGatewayClient {

    /**
     * List of all notes for a patient.
     *
     * @param patId identifier patient
     * @return all notes for this patient
     */
    @GetExchange("/notes/{patId}")
    List<NoteResponseDTO> getNotes(@PathVariable Long patId);

    /**
     * Creates a new note.
     *
     * @param noteRequestDTO the note to create
     * @return the created note
     */
    @PostExchange("/notes/new")
    NoteResponseDTO createNote(@RequestBody NoteRequestDTO noteRequestDTO);

}
