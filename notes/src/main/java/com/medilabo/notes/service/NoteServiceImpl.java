package com.medilabo.notes.service;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.repository.NoteRepository;
import com.medilabo.notes.service.interfaces.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public List<Note> getNotesHistory(Long patId) {
        return noteRepository.findByPatId(patId);
    }

    @Override
    public Note newNoteToPatient(Note note) {
        return  noteRepository.save(note) ;
    }
}
