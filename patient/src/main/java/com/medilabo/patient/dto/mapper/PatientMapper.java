package com.medilabo.patient.dto.mapper;

import com.medilabo.patient.dto.PatientRequestDTO;
import com.medilabo.patient.model.Patient;
import org.springframework.stereotype.Component;


/**
 * Converts data between {@link PatientRequestDTO} and {@link Patient}
 */
@Component
public class PatientMapper {

    /**
     * Get a new {@link Patient} from a patientRequestDTO.
     *
     * @param patientRequestDTO validated request payload
     * @return a new {@code Patient} for patient creation
     */
    public Patient toEntity(PatientRequestDTO  patientRequestDTO) {
        Patient patient = new Patient();
        return getPatient(patient, patientRequestDTO);
    }

    /**
     * Applies a patientRequestDTO's data onto an existing {@link Patient}.
     * <p>
     * Full-replacement: every field is overwritten, the entity's {@code id} is untouched.
     *
     * @param patient the existing patient to update
     * @param patientRequestDTO validated request payload
     * @return the same {@code patient} instance, updated
     */
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
