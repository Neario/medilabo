package com.medilabo.patient.dto;

import com.medilabo.patient.model.enumeration.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Validated payload for PatientRequest
 * <p>
 *
 * @param firstName patient's firstname 2 to 100 characters
 * @param lastName patient's lastname 2 to 100 characters
 * @param birthDate patient's birthdate required
 * @param gender patient's gender optional
 * @param phoneNumber patient's phone number optional
 * @param address patient's address optional
 */
public record PatientRequestDTO(

        @NotBlank
        @Size(min = 2, max = 100)
        String firstName,

        @NotBlank
        @Size(min = 2, max = 100)
        String lastName,

        @NotNull
        LocalDate birthDate,

        Gender gender,
        String phoneNumber,
        String address

) {}
