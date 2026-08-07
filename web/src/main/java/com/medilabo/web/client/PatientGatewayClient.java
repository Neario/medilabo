package com.medilabo.web.client;

import com.medilabo.web.dto.PatientResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface PatientGatewayClient {

    @GetExchange("/patients")
    List<PatientResponseDTO> getPatients();
    @GetExchange("/patients/{id}")
    PatientResponseDTO getPatientById(@PathVariable Long id);
}
