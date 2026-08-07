package com.medilabo.web.service;

import com.medilabo.web.client.PatientGatewayClient;
import com.medilabo.web.dto.PatientResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientGatewayService implements PatientGatewayClient {

    private final PatientGatewayClient patientGatewayClient;

    @Override
    public List<PatientResponseDTO> getPatients() {
        return patientGatewayClient.getPatients();
    }

    @Override
    public PatientResponseDTO getPatientById(Long id) {
        return patientGatewayClient.getPatientById(id);
    }
}
