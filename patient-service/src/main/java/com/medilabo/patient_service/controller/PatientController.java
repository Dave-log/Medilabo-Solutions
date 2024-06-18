package com.medilabo.patient_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PatientController {

    @GetMapping("/patients")
    public String getPatients() {
        return "List of patients";
    }
}
