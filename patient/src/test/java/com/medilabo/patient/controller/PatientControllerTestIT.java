package com.medilabo.patient.controller;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import jakarta.transaction.Transactional;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Transactional
public class PatientControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientRepository patientRepository;

    private Patient patient;
    ObjectWriter objectWriter =  new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void setUp() {
        patient = new Patient();
        patient.setFirstName("Mika");
        patient.setLastName("Mika");
        patient.setBirthDate(LocalDate.now());
        patientRepository.save(patient);
    }

    @Test
    public void should_return_all_patients() throws Exception {
        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void should_return_patient_by_id() throws Exception {
        mockMvc.perform(get("/patients/" +  patient.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Mika"))
            .andExpect(jsonPath("$.lastName").value("Mika"));
    }

    @Test
    public void should_throw_exception_when_patient_id_not_found() throws Exception {
        mockMvc.perform(get("/patients/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_created_patient() throws Exception {
        Patient patient = new Patient();
        patient.setFirstName("Test");
        patient.setLastName("Test");
        patient.setBirthDate(LocalDate.now());

        String content = objectWriter.writeValueAsString(patient);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("Test"));
    }

    @Test
    public void should_throw_exception_when_create_patient_already_exist() throws Exception {
        String content = objectWriter.writeValueAsString(patient);
        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isConflict());
    }

    @Test
    public void should_return_updated_patient() throws Exception {
        patient.setFirstName("Updated");
        mockMvc.perform(put("/patients/" + patient.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(patient)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Updated"))
            .andExpect(jsonPath("$.lastName").value("Mika"));
    }

    @Test
    public void should_throw_exception_when_update_patient_not_found() throws Exception {
        String content = objectWriter.writeValueAsString(patient);
        mockMvc.perform(put("/patients/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_void_when_delete_patient() throws Exception {
        mockMvc.perform(delete("/patients/" + patient.getId())).andExpect(status().isNoContent());
    }

    @Test
    public void should_throw_exception_when_delete_patient_not_found() throws Exception {
        mockMvc.perform(delete("/patients/99"))
                .andExpect(status().isNotFound());
    }
}
