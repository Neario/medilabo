package com.medilabo.web.dto;

import com.medilabo.web.dto.enumeration.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Patient data
 *
 * @param id patient's identifier
 * @param firstName patient's firstname
 * @param lastName patient's lastname
 * @param birthDate patient's birthdate
 * @param gender patient's gender
 * @param phoneNumber patient's phone number
 * @param address patient's address
 */
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
