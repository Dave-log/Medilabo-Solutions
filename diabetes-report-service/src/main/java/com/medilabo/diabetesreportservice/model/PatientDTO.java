package com.medilabo.diabetesreportservice.model;

import java.util.Date;

public record PatientDTO(
        Integer id,
        String firstName,
        String lastName,
        String gender,
        Date birthdate
) {}
