package com.medilabo.web.dto;

import com.medilabo.web.dto.enumeration.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record PatientResponseDTO(
        Long id,
        String firstName,
        String lastName,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthDate,
        Gender gender,
        String phoneNumber,
        String address
) {}
