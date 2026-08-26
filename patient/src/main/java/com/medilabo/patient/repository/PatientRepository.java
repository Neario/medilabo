package com.medilabo.patient.repository;

import com.medilabo.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * Spring Data JPA repository for {@link Patient}
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Verify if {@link Patient} already exist , with firstname, lastname and birthdate
     *
     * @param firstName patient's firstname
     * @param lastName patient's lastname
     * @param birthDate patient's birth ate
     * @return {@code true} if {@link Patient} already exists
     */
    boolean existsByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, LocalDate birthDate);
}
