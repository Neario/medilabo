package com.medilabo.risk.dto;

import com.medilabo.risk.dto.enumeration.Gender;

import java.time.LocalDate;

public record PatientResponseDTO(
        LocalDate birthDate,
        Gender gender
) {
}
