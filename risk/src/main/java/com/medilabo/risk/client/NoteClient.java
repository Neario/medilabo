package com.medilabo.risk.client;

import com.medilabo.risk.dto.NoteResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface NoteClient {

    @GetExchange("/notes/{patId}")
    List<NoteResponseDTO> getNotes(@PathVariable long patId);
}
