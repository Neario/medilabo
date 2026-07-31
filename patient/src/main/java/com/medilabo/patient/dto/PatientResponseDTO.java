package com.medilabo.patient.dto;

import com.medilabo.patient.model.enumeration.Gender;

import java.time.LocalDate;

public record PatientResponseDTO(
        Long id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        Gender gender,
        String phoneNumber,
        String address
) {}
