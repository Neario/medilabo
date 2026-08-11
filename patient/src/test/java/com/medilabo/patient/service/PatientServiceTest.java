package com.medilabo.patient.service;

import com.medilabo.patient.exception.AlreadyExistException;
import com.medilabo.patient.exception.NotFoundException;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    PatientServiceImpl patientServiceImpl;

    private Patient patient;

    @BeforeEach
    public void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Mika");
        patient.setLastName("Mika");
        patient.setBirthDate(LocalDate.now());
    }

    @Test
    public void shouldReturnAllPatients() {
        when(patientRepository.findAll()).thenReturn(List.of(new Patient()));
        List<Patient> patients = patientServiceImpl.findAll();
        Assertions.assertNotNull(patients);
        Assertions.assertEquals(1,patients.size());
        verify(patientRepository,times(1)).findAll();
    }

    @Test
    public void shouldReturnPatientById() {
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        Patient result = patientServiceImpl.getById(patient.getId());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(patient.getId(),result.getId());
        verify(patientRepository,times(1)).findById(patient.getId());

    }
    @Test
    public void ShouldThrowNotFoundExceptionWhenPatientByIdNotFound() {
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> patientServiceImpl.getById(patient.getId()));

    }

    @Test
    public void shouldSaveNewPatient() {
        Patient patient = new Patient();
        when(patientRepository.existsByFirstNameAndLastNameAndBirthDate(patient.getFirstName(), patient.getLastName()
                , patient.getBirthDate())).thenReturn(false);
        when(patientRepository.save(patient)).thenReturn(patient);

        patientServiceImpl.save(patient);

        verify(patientRepository, times(1)).save(patient);
        Assertions.assertNotNull(patient);
        Assertions.assertEquals(patientServiceImpl.save(patient), patient);
    }

    @Test
    public void shouldReturnAlreadyExistingPatient() {
        Patient patient = new Patient();
        when(patientRepository.existsByFirstNameAndLastNameAndBirthDate(patient.getFirstName(), patient.getLastName()
                , patient.getBirthDate())).thenReturn(true);
        Assertions.assertThrows(AlreadyExistException.class, () -> patientServiceImpl.save(patient));
        verify(patientRepository, times(0)).save(patient);
    }

    @Test
    public void shouldUpdatePatient() {
        when(patientRepository.existsById(patient.getId())).thenReturn(true);
        when(patientRepository.save(patient)).thenReturn(patient);
        Patient result = patientServiceImpl.update(patient.getId(), patient);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(patient, result);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenUpdatePatientByIdNotFound() {
        when(patientRepository.existsById(patient.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> patientServiceImpl.update(patient.getId(), patient));
        verify(patientRepository, times(0)).save(patient);
    }

    @Test
    public void shouldDeletePatient() {
        when(patientRepository.existsById(patient.getId())).thenReturn(true);
        patientServiceImpl.deleteById(patient.getId());
        verify(patientRepository, times(1)).deleteById(patient.getId());
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenDeletePatientByIdNotFound() {
        when(patientRepository.existsById(patient.getId())).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class, () -> patientServiceImpl.deleteById(patient.getId()));
        verify(patientRepository, times(0)).deleteById(patient.getId());
    }

}
