package com.medilabo.web.service;

import com.medilabo.web.client.PatientGatewayClient;
import com.medilabo.web.dto.PatientRequestDTO;
import com.medilabo.web.dto.PatientResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link PatientGatewayClient} implementation
 */
@Service
@RequiredArgsConstructor
public class PatientGatewayService implements PatientGatewayClient {

    private final PatientGatewayClient patientGatewayClient;

    /** {@inheritDoc} */
    @Override
    public List<PatientResponseDTO> getPatients() {
        return patientGatewayClient.getPatients();
    }

    /** {@inheritDoc} */
    @Override
    public PatientResponseDTO getPatientById(Long id) {
        return patientGatewayClient.getPatientById(id);
    }

    /** {@inheritDoc} */
    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        return patientGatewayClient.createPatient(patientRequestDTO);
    }

    /** {@inheritDoc} */
    @Override
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO) {
        return patientGatewayClient.updatePatient(id, patientRequestDTO);
    }


}
