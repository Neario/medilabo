package com.medilabo.risk.client;

import com.medilabo.risk.dto.PatientResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface PatientClient {

    @GetExchange("/patients/{id}")
    PatientResponseDTO getPatient(@PathVariable long id);
}
