package com.medilabo.patient.service.interfaces;

import com.medilabo.patient.model.Patient;

import java.util.List;
import java.util.Optional;

/**
 * CRUD operations for {@link Patient}.
 */
public interface PatientService {

    /**
     * find a patient by id.
     *
     * @param id identifier of the patient
     * @return the patient, or empty if patient not found
     */
    Optional<Patient> findById(Long id);

    /**
     * find a patient by id.
     *
     * @param id identifier of the patient
     * @return the matching patient
     * @throws com.medilabo.patient.exception.NotFoundException if patient not found
     */
    Patient getById(Long id);

    /**
     * @return all patient
     */
    List<Patient> findAll();

    /**
     * Save new patient.
     *
     * @param patient the patient to create
     * @return saved patient
     * @throws com.medilabo.patient.exception.AlreadyExistException if a patient with the same firstname, lastname and birthdate already exists
     */
    Patient save(Patient patient);

    /**
     * Update data of an existing patient.
     *
     * @param id identifier of the patient to update
     * @param patient the update data for this patient
     * @return the updated patient
     * @throws com.medilabo.patient.exception.NotFoundException if patient not found
     */
    Patient update(Long id, Patient patient);

    /**
     * Deletes a patient.
     *
     * @param id identifier of the patient to delete
     * @throws com.medilabo.patient.exception.NotFoundException if patient not found
     */
    void deleteById(Long id);
}
