package com.medilabo.patient.dto.mapper;

import com.medilabo.patient.dto.PatientRequestDTO;
import com.medilabo.patient.dto.PatientResponseDTO;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.model.enumeration.Gender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequestDTO  patientRequestDTO) {
        Patient patient = new Patient();
        return getPatient(patient, patientRequestDTO);
    }

    public Patient updateEntity(Patient patient, PatientRequestDTO patientRequestDTO) {
        return getPatient(patient, patientRequestDTO);
    }

    public PatientResponseDTO toResponseDTO(Patient patient) {
        return new PatientResponseDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getBirthDate(),
                patient.getGender(),
                patient.getPhoneNumber(),
                patient.getAddress()
        );
    }

    public List<PatientResponseDTO> toResponseDTOList(List<Patient> patients) {
        return patients.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private Patient getPatient(Patient patient, PatientRequestDTO patientRequestDTO) {
        patient.setFirstName(patientRequestDTO.firstName());
        patient.setLastName(patientRequestDTO.lastName());
        patient.setBirthDate(patientRequestDTO.birthDate());
        patient.setGender(patientRequestDTO.gender());
        patient.setPhoneNumber(patientRequestDTO.phoneNumber());
        patient.setAddress(patientRequestDTO.address());
        return patient;
    }
}
