package com.medilabo.notes.service;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.repository.NoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    NoteServiceImpl noteServiceImpl;

    private Note note;

    @BeforeEach
    public void setUp() {
        note = new Note();
        note.setId("1");
        note.setPatId(1L);
        note.setPatient("Mika Mika");
        note.setNote("TestNote");
    }

    @Test
    public void shouldReturnNotesHistoryForPatient() {
        when(noteRepository.findByPatId(note.getPatId())).thenReturn(List.of(note));

        List<Note> notes = noteServiceImpl.getNotesHistory(note.getPatId());

        Assertions.assertNotNull(notes);
        Assertions.assertEquals(1, notes.size());
        verify(noteRepository, times(1)).findByPatId(note.getPatId());
    }

    @Test
    public void shouldReturnEmptyListWhenPatientHasNoNotes() {
        when(noteRepository.findByPatId(note.getPatId())).thenReturn(List.of());

        List<Note> notes = noteServiceImpl.getNotesHistory(note.getPatId());

        Assertions.assertNotNull(notes);
        Assertions.assertTrue(notes.isEmpty());
    }

    @Test
    public void shouldSaveNewNote() {
        when(noteRepository.save(note)).thenReturn(note);

        Note result = noteServiceImpl.newNoteToPatient(note);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(note, result);
        verify(noteRepository, times(1)).save(note);
    }
}
