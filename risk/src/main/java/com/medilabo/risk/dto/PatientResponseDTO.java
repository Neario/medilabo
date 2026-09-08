package com.medilabo.risk.dto;

import com.medilabo.risk.dto.enumeration.Gender;

import java.time.LocalDate;

/**
 * Patient data as returned by the {@code patient} service
 *
 * @param birthDate patient's birthdate
 * @param gender patient's gender
 */
public record PatientResponseDTO(
        LocalDate birthDate,
        Gender gender
) {
}
