package com.medilabo.diabetesreportservice.model;

import java.time.LocalDate;

public record PatientDTO(
        Integer id,
        String firstName,
        String lastName,
        String gender,
        LocalDate birthdate
) {}
