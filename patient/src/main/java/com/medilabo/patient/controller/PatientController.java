package com.medilabo.patient.controller;

import com.medilabo.patient.dto.PatientRequestDTO;
import com.medilabo.patient.dto.mapper.PatientMapper;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.interfaces.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing CRUD endpoints for {@link Patient}.
 * <p>
 * Accepts {@link PatientRequestDTO} on write operations and converts it to a
 * {@link Patient} via {@link PatientMapper} before delegating to {@link PatientService}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    /**
     * Lists of all patient.
     *
     * @return {@code 200 OK} with the full list of patients
     */
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.findAll();
        return ResponseEntity.ok().body(patients);
    }

    /**
     * Get a patient by id.
     *
     * @param id identifier of the patient
     * @return {@code 200 OK} with the patient
     * @throws com.medilabo.patient.exception.NotFoundException if patient not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getById(id);
        return ResponseEntity.ok().body(patient);
    }

    /**
     * Creates a new patient.
     *
     * @param patientRequestDTO validated patient's data
     * @return {@code 201 Created} with the patient's data
     * @throws com.medilabo.patient.exception.AlreadyExistException if a patient already exist
     */
    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        Patient patient = patientService.save(patientMapper.toEntity(patientRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    /**
     * Update an existing patient's data.
     *
     * @param id identifier of the patient to update
     * @param patientRequestDTO validated patient's data
     * @return {@code 200 OK} with the updated patient's data
     * @throws com.medilabo.patient.exception.NotFoundException if patient not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        Patient patient = patientService.update(id,patientMapper.toEntity(patientRequestDTO));
        return ResponseEntity.ok().body(patient);
    }

    /**
     * Deletes a patient.
     *
     * @param id identifier of the patient to delete
     * @return {@code 204 No Content} on success
     * @throws com.medilabo.patient.exception.NotFoundException if patient not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
