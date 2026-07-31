package com.medilabo.patient.dto;

import com.medilabo.patient.model.enumeration.Gender;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

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
