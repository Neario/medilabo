package com.medilabo.patient.service;


import com.medilabo.patient.dto.PatientRequestDTO;
import com.medilabo.patient.dto.mapper.PatientMapper;
import com.medilabo.patient.exception.AlreadyExistException;
import com.medilabo.patient.exception.NotFoundException;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import com.medilabo.patient.service.interfaces.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService  {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient getById(Long id) {
        return patientRepository.findById(id).orElseThrow(()->new NotFoundException(Patient.class.getSimpleName(), id));
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient save(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByFirstNameAndLastNameAndBirthDate(patientRequestDTO.firstName(),
                patientRequestDTO.lastName(),
                patientRequestDTO.birthDate())) {
            throw new AlreadyExistException(Patient.class.getSimpleName());
        }
        Patient patient = patientMapper.toEntity(patientRequestDTO);
        return patientRepository.save(patient);
    }

    @Override
    public Patient update(Long id, PatientRequestDTO patientRequestDTO) {
         Patient patientUpdated =
                patientRepository.findById(id).orElseThrow(() -> new NotFoundException(Patient.class.getSimpleName(),
                        id));
        patientUpdated = patientMapper.updateEntity(patientUpdated, patientRequestDTO);
        return patientRepository.save(patientUpdated);
    }

    @Override
    public void deleteById(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new NotFoundException(Patient.class.getSimpleName(), id);
        }
        patientRepository.deleteById(id);
    }
}
