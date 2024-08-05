package com.medilabo.patient_service.service;

import com.medilabo.patient_service.model.Gender;
import com.medilabo.patient_service.model.Patient;
import com.medilabo.patient_service.model.PatientDTO;
import com.medilabo.patient_service.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient testPatient;
    private PatientDTO testPatientDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test patient and DTO
        testPatient = new Patient();
        testPatient.setId(1);
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setBirthdate(LocalDate.parse("1970-01-01"));
        testPatient.setGender(Gender.M);

        testPatientDTO = new PatientDTO(
                1,
                "John",
                "Doe",
                LocalDate.parse("1970-01-01"),
                Gender.M,
                "1 Fake St",
                "000-000-0000");
    }

    @Test
    public void testGetPatientDTOById_Found() {
        when(patientRepository.findById(1)).thenReturn(Optional.of(testPatient));

        Optional<PatientDTO> patientDTO = patientService.getPatientById(1);

        assertTrue(patientDTO.isPresent());
        assertEquals(patientDTO.get().firstName(), "John");
        assertEquals(patientDTO.get().lastName(), "Doe");
    }

    @Test
    public void testGetPatientDTOById_NotFound() {
        when(patientRepository.findById(anyInt())).thenReturn(Optional.empty());

        Optional<PatientDTO> patientDTO = patientService.getPatientById(1);

        assertFalse(patientDTO.isPresent());
    }

    @Test
    public void testSavePatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        PatientDTO savedPatientDTO = patientService.savePatient(testPatientDTO);

        assertNotNull(savedPatientDTO);
        assertEquals(savedPatientDTO.firstName(), "John");
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    public void testUpdatePatient_Found() {
        when(patientRepository.findById(1)).thenReturn(Optional.of(testPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        Optional<PatientDTO> updatedPatientDTO = patientService.updatePatient(1, testPatientDTO);

        assertTrue(updatedPatientDTO.isPresent());
        assertEquals(updatedPatientDTO.get().firstName(), "John");
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    public void testUpdatePatient_NotFound() {
        when(patientRepository.findById(anyInt())).thenReturn(Optional.empty());

        Optional<PatientDTO> updatedPatientDTO = patientService.updatePatient(1, testPatientDTO);

        assertFalse(updatedPatientDTO.isPresent());
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    public void testDeletePatient_Found() {
        when(patientRepository.existsById(1)).thenReturn(true);

        boolean result = patientService.deletePatient(1);

        assertTrue(result);
        verify(patientRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeletePatient_NotFound() {
        when(patientRepository.existsById(anyInt())).thenReturn(false);

        boolean result = patientService.deletePatient(1);

        assertFalse(result);
        verify(patientRepository, never()).deleteById(anyInt());
    }
}
