package com.medilabo.notes.service;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.repository.NoteRepository;
import com.medilabo.notes.service.interfaces.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link NoteService} implementation
 */
@Service
@RequiredArgsConstructor

public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    /** {@inheritDoc} */
    @Override
    public List<Note> getNotesHistory(Long patId) {
        return noteRepository.findByPatId(patId);
    }

    /** {@inheritDoc} */
    @Override
    public Note newNoteToPatient(Note note) {
        return  noteRepository.save(note) ;
    }
}
