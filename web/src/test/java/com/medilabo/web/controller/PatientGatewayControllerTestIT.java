package com.medilabo.web.controller;

import com.medilabo.web.client.NoteGatewayClient;
import com.medilabo.web.client.PatientGatewayClient;
import com.medilabo.web.client.RiskGatewayClient;
import com.medilabo.web.dto.NoteRequestDTO;
import com.medilabo.web.dto.NoteResponseDTO;
import com.medilabo.web.dto.PatientRequestDTO;
import com.medilabo.web.dto.PatientResponseDTO;
import com.medilabo.web.dto.RiskResponseDTO;
import com.medilabo.web.dto.enumeration.Gender;
import com.medilabo.web.dto.enumeration.RiskLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class PatientGatewayControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientGatewayClient patientGatewayClient;

    @MockitoBean
    private NoteGatewayClient noteGatewayClient;

    @MockitoBean
    private RiskGatewayClient riskGatewayClient;

    private final PatientResponseDTO patient = new PatientResponseDTO(1L, "Mika", "Mika",
            LocalDate.of(1988, 12, 5), Gender.M, "0600000000", "1 rue du test");

    @Test
    @WithMockUser(roles = "ORGANIZER")
    public void should_return_patient_list_view() throws Exception {
        when(patientGatewayClient.getPatients()).thenReturn(List.of(patient));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/list"))
                .andExpect(model().attribute("patients", List.of(patient)));
    }

    @Test
    @WithMockUser(roles = "ORGANIZER")
    public void should_return_patient_detail_view() throws Exception {
        NoteResponseDTO note = new NoteResponseDTO("note", 1L, "Mika Mika", "TestNote");

        when(patientGatewayClient.getPatientById(1L)).thenReturn(patient);
        when(noteGatewayClient.getNotes(1L)).thenReturn(List.of(note));
        when(riskGatewayClient.getRisk(1L)).thenReturn(new RiskResponseDTO(RiskLevel.NONE));

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/view"))
                .andExpect(model().attribute("patient", patient))
                .andExpect(model().attribute("notes", List.of(note)));
    }

    @Test
    @WithMockUser(roles = "ORGANIZER")
    public void should_return_create_form_view() throws Exception {
        mockMvc.perform(get("/patients/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/create"))
                .andExpect(model().attribute("formAction", "/patients"));
    }

    @Test
    @WithMockUser(roles = "ORGANIZER")
    public void should_create_patient_and_redirect() throws Exception {
        when(patientGatewayClient.createPatient(any(PatientRequestDTO.class))).thenReturn(patient);

        mockMvc.perform(post("/patients")
                        .with(csrf())
                        .param("firstName", "Mika")
                        .param("lastName", "Mika")
                        .param("birthDate", "1988-12-05")
                        .param("gender", "M")
                        .param("phoneNumber", "0600000000")
                        .param("address", "1 rue du test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));

        verify(patientGatewayClient).createPatient(any(PatientRequestDTO.class));
    }

    @Test
    @WithMockUser(roles = "ORGANIZER")
    public void should_return_create_form_view_with_error() throws Exception {
        when(patientGatewayClient.createPatient(any(PatientRequestDTO.class)))
                .thenThrow(new RuntimeException("Resource Patient already exist"));

        mockMvc.perform(post("/patients")
                        .with(csrf())
                        .param("firstName", "Mika")
                        .param("lastName", "Mika")
                        .param("birthDate", "1988-12-05")
                        .param("gender", "M")
                        .param("phoneNumber", "0600000000")
                        .param("address", "1 rue du test"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/create"))
                .andExpect(model().attribute("formAction", "/patients"))
                .andExpect(model().attribute("error", "Resource Patient already exist"));
    }

    @Test
    @WithMockUser(roles = "ORGANIZER")
    public void should_return_create_form_view_on_validation_error() throws Exception {
        mockMvc.perform(post("/patients")
                        .with(csrf())
                        .param("firstName", "")
                        .param("lastName", "Mika")
                        .param("birthDate", "1990-01-01")
                        .param("gender", "M"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/create"));
    }

    @Test
    @WithMockUser(roles = "ORGANIZER")
    public void should_return_edit_form_view() throws Exception {
        when(patientGatewayClient.getPatientById(1L)).thenReturn(patient);

        mockMvc.perform(get("/patients/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/create"))
                .andExpect(model().attribute("formAction", "/patients/1"));
    }

    @Test
    @WithMockUser(roles = "ORGANIZER")
    public void should_update_patient_and_redirect() throws Exception {
        when(patientGatewayClient.updatePatient(1L, any(PatientRequestDTO.class)))
                .thenReturn(patient);

        mockMvc.perform(post("/patients/1")
                        .with(csrf())
                        .param("firstName", "MikaUpdate")
                        .param("lastName", "Mika")
                        .param("birthDate", "1988-12-05")
                        .param("gender", "M")
                        .param("phoneNumber", "0600000000")
                        .param("address", "1 rue du test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));
    }

    @Test
    @WithMockUser(roles = "ORGANIZER")
    public void should_return_edit_form_view_with_error() throws Exception {
        when(patientGatewayClient.updatePatient(1L, any(PatientRequestDTO.class)))
                .thenThrow(new RuntimeException("Resource Patient not found with id = '1'"));

        mockMvc.perform(post("/patients/1")
                        .with(csrf())
                        .param("firstName", "MikaUpdate")
                        .param("lastName", "Mika")
                        .param("birthDate", "1988-12-05")
                        .param("gender", "M")
                        .param("phoneNumber", "0600000000")
                        .param("address", "1 rue du test"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/create"))
                .andExpect(model().attribute("formAction", "/patients/1"))
                .andExpect(model().attribute("error", "Resource Patient not found with id = '1'"));
    }

    @Test
    @WithMockUser(roles = "PRACTITIONER")
    public void should_return_new_note_form_view() throws Exception {
        when(patientGatewayClient.getPatientById(1L)).thenReturn(patient);

        mockMvc.perform(get("/patients/1/notes/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes/create"))
                .andExpect(model().attribute("patient", patient));
    }

    @Test
    @WithMockUser(roles = "PRACTITIONER")
    public void should_add_note_and_redirect() throws Exception {
        NoteResponseDTO createdNote = new NoteResponseDTO("note", 1L, "Mika Mika", "Nouvelle note");
        when(noteGatewayClient.createNote(any(NoteRequestDTO.class))).thenReturn(createdNote);

        mockMvc.perform(post("/patients/1/notes")
                        .with(csrf())
                        .param("patient", "Mika Mika")
                        .param("note", "Nouvelle note"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/1"));

        verify(noteGatewayClient).createNote(new NoteRequestDTO(1L, "Mika Mika", "Nouvelle note"));
    }
}
