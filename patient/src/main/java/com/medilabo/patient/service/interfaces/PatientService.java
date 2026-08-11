package com.medilabo.patient.service.interfaces;

import com.medilabo.patient.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    Optional<Patient> findById(Long id);
    Patient getById(Long id);
    List<Patient> findAll();
    Patient save(Patient patient);
    Patient update(Long id, Patient patient);
    void deleteById(Long id);
}
