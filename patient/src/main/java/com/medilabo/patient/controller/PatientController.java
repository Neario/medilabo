package com.medilabo.patient.controller;

import com.medilabo.patient.dto.PatientRequestDTO;
import com.medilabo.patient.dto.PatientResponseDTO;
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
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        List<Patient> patients = patientService.findAll();
        return ResponseEntity.ok().body(patientMapper.toResponseDTOList(patients));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getById(id);
        return ResponseEntity.ok().body(patientMapper.toResponseDTO(patient));
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        Patient patient = patientService.save(patientRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientMapper.toResponseDTO(patient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        Patient patient = patientService.update(id,patientRequestDTO);
        return ResponseEntity.ok().body(patientMapper.toResponseDTO(patient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
