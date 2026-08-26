package com.medilabo.notes.service.interfaces;

import com.medilabo.notes.model.Note;

import java.util.List;

/**
 * CRUD operations for {@link Note}.
 */
public interface NoteService {
    /**
     * Find all {@link Note} for a Patient
     * @param patId patient's identifier
     * @return list of {@link Note}
     */
    List<Note> getNotesHistory(Long patId);

    /**
     * Create a new {@link Note} for a Patient
     * @param note new {@link Note}
     * @return {@link Note}
     */
    Note newNoteToPatient(Note note);
}
