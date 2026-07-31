package com.medilabo.patient.service.interfaces;

import com.medilabo.patient.dto.PatientRequestDTO;
import com.medilabo.patient.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    Optional<Patient> findById(Long id);
    Patient getById(Long id);
    List<Patient> findAll();
    Patient save(PatientRequestDTO patientRequestDTO);
    Patient update(Long id, PatientRequestDTO patientRequestDTO);
    void deleteById(Long id);
}
