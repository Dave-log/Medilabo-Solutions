package com.medilabo.patient_service.controller;

import com.medilabo.patient_service.model.Patient;
import com.medilabo.patient_service.model.PatientDTO;
import com.medilabo.patient_service.repository.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @CrossOrigin
    @GetMapping
    public List<PatientDTO> getPatients() {
        return patientRepository.findAll()
                .stream()
                .sorted((p1, p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName()))
                .map(this::convertToDTO)
                .toList();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Integer id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.map(value -> new ResponseEntity<>(convertToDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<PatientDTO> savePatient(@Valid @RequestBody PatientDTO patientDTO) {
        Patient patient = convertToEntity(patientDTO);
        Patient savedPatient = patientRepository.save(patient);
        return new ResponseEntity<>(convertToDTO(savedPatient), HttpStatus.CREATED);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Integer id, @Valid @RequestBody PatientDTO patientDTO) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            updateEntityFromDTO(patientDTO, patient);
            Patient updatedPatient = patientRepository.save(patient);
            return new ResponseEntity<>(convertToDTO(updatedPatient), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private PatientDTO convertToDTO(Patient patient) {
        return new PatientDTO(
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
