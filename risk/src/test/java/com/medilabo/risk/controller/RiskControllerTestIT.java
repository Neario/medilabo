package com.medilabo.risk.controller;

import com.medilabo.risk.client.NoteClient;
import com.medilabo.risk.client.PatientClient;
import com.medilabo.risk.dto.NoteResponseDTO;
import com.medilabo.risk.dto.PatientResponseDTO;
import com.medilabo.risk.dto.enumeration.Gender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class RiskControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientClient patientClient;

    @MockitoBean
    private NoteClient noteClient;

    @Test
    public void should_return_none_when_no_trigger_terms() throws Exception {
        when(patientClient.getPatient(1L)).thenReturn(new PatientResponseDTO(LocalDate.now().minusYears(45), Gender.M));
        when(noteClient.getNotes(1L)).thenReturn(List.of(new NoteResponseDTO("Rien à signaler")));

        mockMvc.perform(get("/risk/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value("NONE"));
    }

    @Test
    public void should_return_borderline_for_age_over_30_with_two_triggers() throws Exception {
        when(patientClient.getPatient(2L)).thenReturn(new PatientResponseDTO(LocalDate.now().minusYears(45), Gender.F));
        when(noteClient.getNotes(2L)).thenReturn(List.of(new NoteResponseDTO("Taille, Poids")));

        mockMvc.perform(get("/risk/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value("BORDERLINE"));
    }

    @Test
    public void should_return_in_danger_for_age_over_30_with_six_triggers() throws Exception {
        when(patientClient.getPatient(3L)).thenReturn(new PatientResponseDTO(LocalDate.now().minusYears(50), Gender.M));
        when(noteClient.getNotes(3L)).thenReturn(List.of(new NoteResponseDTO(
                "Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur, Anormal")));

        mockMvc.perform(get("/risk/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value("IN_DANGER"));
    }

    @Test
    public void should_return_early_onset_for_age_over_30_with_eight_triggers() throws Exception {
        when(patientClient.getPatient(4L)).thenReturn(new PatientResponseDTO(LocalDate.now().minusYears(50), Gender.M));
        when(noteClient.getNotes(4L)).thenReturn(List.of(new NoteResponseDTO(
                "Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur, Anormal, Cholestérol, Vertiges")));

        mockMvc.perform(get("/risk/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value("EARLY_ONSET"));
    }

    @Test
    public void should_return_in_danger_for_male_under_30_with_three_triggers() throws Exception {
        when(patientClient.getPatient(5L)).thenReturn(new PatientResponseDTO(LocalDate.now().minusYears(25), Gender.M));
        when(noteClient.getNotes(5L)).thenReturn(List.of(new NoteResponseDTO("Fumeur, Anormal, Cholestérol")));

        mockMvc.perform(get("/risk/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value("IN_DANGER"));
    }

    @Test
    public void should_return_in_danger_for_female_under_30_with_four_triggers() throws Exception {
        when(patientClient.getPatient(6L)).thenReturn(new PatientResponseDTO(LocalDate.now().minusYears(20), Gender.F));
        when(noteClient.getNotes(6L)).thenReturn(List.of(new NoteResponseDTO("Taille, Poids, Cholestérol, Vertiges")));

        mockMvc.perform(get("/risk/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value("IN_DANGER"));
    }

    @Test
    public void should_return_early_onset_for_male_under_30_with_five_triggers() throws Exception {
        when(patientClient.getPatient(7L)).thenReturn(new PatientResponseDTO(LocalDate.now().minusYears(25), Gender.M));
        when(noteClient.getNotes(7L)).thenReturn(List.of(new NoteResponseDTO(
                "Anticorps, Réaction, Taille, Poids, Cholestérol")));

        mockMvc.perform(get("/risk/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value("EARLY_ONSET"));
    }
}
