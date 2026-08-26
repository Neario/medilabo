package com.medilabo.patient.service;

import com.medilabo.patient.exception.AlreadyExistException;
import com.medilabo.patient.exception.NotFoundException;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import com.medilabo.patient.service.interfaces.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * {@link PatientService} implementation
 * <p>
 * CRUD logic , verification before save, update , delete or read DATA
 */
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService  {

    private final PatientRepository patientRepository;

    /** {@inheritDoc} */
    @Override
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    public Patient getById(Long id) {
        return patientRepository.findById(id).orElseThrow(()->new NotFoundException(Patient.class.getSimpleName(), id));
    }

    /** {@inheritDoc} */
    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    /** {@inheritDoc} */
    @Override
    public Patient save(Patient patient) {
        if (patientRepository.existsByFirstNameAndLastNameAndBirthDate(patient.getFirstName(),
                patient.getLastName(),
                patient.getBirthDate())) {
            throw new AlreadyExistException(Patient.class.getSimpleName());
        }
        return patientRepository.save(patient);
    }

    /** {@inheritDoc} */
    @Override
    public Patient update(Long id, Patient patient) {
        if (!patientRepository.existsById(id)) {
            throw new NotFoundException(Patient.class.getSimpleName(), id);
        }
        patient.setId(id);
        return patientRepository.save(patient);
    }

    /** {@inheritDoc} */
    @Override
    public void deleteById(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new NotFoundException(Patient.class.getSimpleName(), id);
        }
        patientRepository.deleteById(id);
    }
}
