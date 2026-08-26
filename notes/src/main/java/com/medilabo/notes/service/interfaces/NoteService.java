package com.medilabo.notes.service.interfaces;

import com.medilabo.notes.model.Note;

import java.util.List;

public interface NoteService {
    List<Note> getNotesHistory(Long patId);
    Note newNoteToPatient(Note note);
}
