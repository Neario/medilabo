package com.medilabo.notes.controller;

import com.medilabo.notes.dto.NoteRequestDTO;
import com.medilabo.notes.model.Note;
import com.medilabo.notes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class NoteControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    private Note note;
    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void setUp() {
        note = new Note();
        note.setPatId(1L);
        note.setPatient("Mika Mika");
        note.setNote("TestNote");
        noteRepository.save(note);
    }

    @Test
    public void should_return_notes_for_patient() throws Exception {
        mockMvc.perform(get("/notes/" + note.getPatId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void should_create_note() throws Exception {
        NoteRequestDTO noteRequestDTO = new NoteRequestDTO(2L, "Test Patient", "Nouvelle note");

        mockMvc.perform(post("/notes/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(noteRequestDTO)))
                .andExpect(status().isCreated());
    }
}