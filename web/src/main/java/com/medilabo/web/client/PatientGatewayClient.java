package com.medilabo.web.client;

import com.medilabo.web.dto.PatientRequestDTO;
import com.medilabo.web.dto.PatientResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

public interface PatientGatewayClient {

    @GetExchange("/patients")
    List<PatientResponseDTO> getPatients();
    @GetExchange("/patients/{id}")
    PatientResponseDTO getPatientById(@PathVariable Long id);
    @PostExchange("/patients")
    PatientResponseDTO createPatient(@RequestBody PatientRequestDTO patientRequestDTO);
    @PutExchange("/patients/{id}")
    PatientResponseDTO updatePatient(@PathVariable Long id, @RequestBody PatientRequestDTO patientRequestDTO);
}
