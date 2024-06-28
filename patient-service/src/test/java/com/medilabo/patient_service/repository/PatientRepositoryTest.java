package com.medilabo.patient_service.repository;

import com.medilabo.patient_service.PatientServiceApplicationTests;
import com.medilabo.patient_service.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@AutoConfigureMockMvc
public class PatientRepositoryTest extends PatientServiceApplicationTests {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testFindAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        assertEquals(patients.size(), 4);

        Patient patient = patients.getFirst();
        assertEquals("TestNone", patient.getLastName());
    }
}
