package com.medilabo.patient.dto.mapper;

import com.medilabo.patient.dto.PatientRequestDTO;
import com.medilabo.patient.model.Patient;
import org.springframework.stereotype.Component;


@Component
public class PatientMapper {

    public Patient toEntity(PatientRequestDTO  patientRequestDTO) {
        Patient patient = new Patient();
        return getPatient(patient, patientRequestDTO);
    }

    public Patient updateEntity(Patient patient, PatientRequestDTO patientRequestDTO) {
        return getPatient(patient, patientRequestDTO);
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
