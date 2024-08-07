package com.medilabo.patient_service.service;

import com.medilabo.patient_service.model.PatientDTO;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<PatientDTO> getPatients();

    Optional<PatientDTO> getPatientById(int id);

    PatientDTO savePatient(PatientDTO patient);

    Optional<PatientDTO> updatePatient(Integer id, PatientDTO patient);

    boolean deletePatient(Integer id);
}
