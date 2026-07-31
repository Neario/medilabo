package com.medilabo.patient.model;

import com.medilabo.patient.model.enumeration.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Firstname is mandatory")
    @Size(min = 2, max = 100, message = "Firstname must be between 2 and 100 characters")
    private String firstName;
    @NotBlank(message = "Lastname is mandatory")
    @Size(min = 1, max = 100, message = "Lastname must be between 1 and 100 characters")
    private String lastName;
    @NotNull(message = "birthdate must not be null")
    @Past(message = "birthdate must be in the past")
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phoneNumber;
    private String address;
}
