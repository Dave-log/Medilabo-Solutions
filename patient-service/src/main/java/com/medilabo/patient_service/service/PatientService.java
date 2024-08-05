package com.medilabo.patient_service.service;

import com.medilabo.patient_service.model.Patient;
import com.medilabo.patient_service.model.PatientDTO;
import com.medilabo.patient_service.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDTO> getPatients() {
        return patientRepository.findAll()
                .stream()
                .sorted((p1, p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName()))
                .map(this::convertToDTO)
                .toList();
    }

    public Optional<PatientDTO> getPatientById(int id) {
        return patientRepository.findById(id)
                .map(this::convertToDTO);
    }

    public PatientDTO savePatient(PatientDTO patientDTO) {
        Patient patient = convertToEntity(patientDTO);
        Patient savedPatient = patientRepository.save(patient);
        return convertToDTO(savedPatient);
    }

    public Optional<PatientDTO> updatePatient(Integer id, PatientDTO patientDTO) {
        return patientRepository.findById(id)
                .map(existingPatient -> {
                    updateEntityFromDTO(patientDTO, existingPatient);
                    Patient updatedPatient = patientRepository.save(existingPatient);
                    return convertToDTO(updatedPatient);
                });
    }

    public boolean deletePatient(Integer id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PatientDTO convertToDTO(Patient patient) {
        return new PatientDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getBirthdate(),
                patient.getGender(),
                patient.getAddress(),
                patient.getPhoneNumber()
        );
    }

    private Patient convertToEntity(PatientDTO dto) {
        Patient patient = new Patient();
        updateEntityFromDTO(dto, patient);
        return patient;
    }

    private void updateEntityFromDTO(PatientDTO dto, Patient patient) {
        patient.setFirstName(dto.firstName());
        patient.setLastName(dto.lastName());
        patient.setBirthdate(dto.birthdate());
        patient.setGender(dto.gender());
        patient.setAddress(dto.address());
        patient.setPhoneNumber(dto.phoneNumber());
    }
}
