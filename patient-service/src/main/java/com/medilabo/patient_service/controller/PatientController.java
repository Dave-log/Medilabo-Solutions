package com.medilabo.patient_service.controller;

import com.medilabo.patient_service.model.PatientDTO;
import com.medilabo.patient_service.service.PatientService;
import com.medilabo.patient_service.service.PatientServiceImpl;
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

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientServiceImpl patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientDTO> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Integer id) {
        Optional<PatientDTO> patientDTO = patientService.getPatientById(id);
        return patientDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PatientDTO> savePatient(@Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO savedPatient = patientService.savePatient(patientDTO);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Integer id, @Valid @RequestBody PatientDTO patientDTO) {
        Optional<PatientDTO> updatedPatient = patientService.updatePatient(id, patientDTO);
        return updatedPatient
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id) {
        boolean deleted = patientService.deletePatient(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
