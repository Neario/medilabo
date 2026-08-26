package com.medilabo.web.client;

import com.medilabo.web.dto.NoteRequestDTO;
import com.medilabo.web.dto.NoteResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

public interface NoteGatewayClient {
    @GetExchange("/notes/{patId}")
    List<NoteResponseDTO> getNotes(@PathVariable Long patId);

    @PostExchange("/notes/new")
    NoteResponseDTO createNote(@RequestBody NoteRequestDTO noteRequestDTO);

}
