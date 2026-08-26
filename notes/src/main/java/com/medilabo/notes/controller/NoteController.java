package com.medilabo.notes.controller;

import com.medilabo.notes.dto.NoteRequestDTO;
import com.medilabo.notes.model.Note;
import com.medilabo.notes.service.interfaces.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing CRUD endpoints for {@link Note}.
 * <p>
 * {@link NoteRequestDTO} on write operations and converts it to a
 * {@link Note} before delegating to {@link NoteService}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    /**
     * Lists of all note for a patient.
     *
     * @return {@code 200 OK} with the full list of note
     */
    @GetMapping("/{patId}")
    public ResponseEntity<List<Note>> getNotes(@PathVariable Long patId){
        List<Note> notes = noteService.getNotesHistory(patId);
        return ResponseEntity.ok(notes);
    }

    /**
     * Creates a new patient.
     *
     * @param noteRequestDTO validated note data
     * @return {@code 201 Created} with the note data
     */
    @PostMapping("/new")
    public ResponseEntity<Note> newNote(@Valid @RequestBody final NoteRequestDTO noteRequestDTO){
        final Note note = noteService.newNoteToPatient(noteRequestDTO.toNote());
        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }
}
