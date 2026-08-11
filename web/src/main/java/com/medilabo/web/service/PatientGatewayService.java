package com.medilabo.web.service;

import com.medilabo.web.client.PatientGatewayClient;
import com.medilabo.web.dto.PatientRequestDTO;
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

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        return patientGatewayClient.createPatient(patientRequestDTO);
    }

    @Override
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO) {
        return patientGatewayClient.updatePatient(id, patientRequestDTO);
    }


}
