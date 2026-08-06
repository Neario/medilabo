package com.medilabo.web.dto;

import com.medilabo.web.dto.enumeration.Gender;

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
