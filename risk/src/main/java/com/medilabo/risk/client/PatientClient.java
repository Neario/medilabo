package com.medilabo.risk.client;

import com.medilabo.risk.dto.PatientResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

/**
 * HTTP client for the {@code patient} endpoints
 */
public interface PatientClient {

    /**
     * Get a single patient.
     *
     * @param id identifier of the patient
     * @return a PatientResponseDTO with birthdate and gender
     */
    @GetExchange("/patients/{id}")
    PatientResponseDTO getPatient(@PathVariable long id);
}
