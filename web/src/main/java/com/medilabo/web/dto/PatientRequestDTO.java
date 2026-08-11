package com.medilabo.web.dto;

import com.medilabo.web.dto.enumeration.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record PatientRequestDTO(
        @NotBlank(message = "firstname is mandatory")
        @Size(min = 2, max = 100)
        String firstName,

        @NotBlank(message = "lastName is mandatory")
        @Size(min = 2, max = 100)
        String lastName,

        @NotNull(message = "birthDate must be not empty")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthDate,

        Gender gender,
        String phoneNumber,
        String address
) {
}
