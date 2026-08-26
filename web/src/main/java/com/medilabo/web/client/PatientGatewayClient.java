package com.medilabo.web.client;

import com.medilabo.web.dto.PatientRequestDTO;
import com.medilabo.web.dto.PatientResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

/**
 * HTTP client for the {@code patient} endpoints
 */
public interface PatientGatewayClient {

    /**
     * List of all patients.
     *
     * @return all patient
     */
    @GetExchange("/patients")
    List<PatientResponseDTO> getPatients();

    /**
     * Get a single patient.
     *
     * @param id identifier patient
     * @return patient
     */
    @GetExchange("/patients/{id}")
    PatientResponseDTO getPatientById(@PathVariable Long id);

    /**
     * Creates a new patient.
     *
     * @param patientRequestDTO with validated patient's data
     * @return the created patient
     */
    @PostExchange("/patients")
    PatientResponseDTO createPatient(@RequestBody PatientRequestDTO patientRequestDTO);

    /**
     * Updates an existing patient's data.
     *
     * @param id identifier patient to update
     * @param patientRequestDTO with validated patient's data
     * @return the updated patient
     */
    @PutExchange("/patients/{id}")
    PatientResponseDTO updatePatient(@PathVariable Long id, @RequestBody PatientRequestDTO patientRequestDTO);
}
