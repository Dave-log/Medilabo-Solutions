package com.medilabo.patient_service.model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record PatientDTO(
   @NotNull(message = "Id cannot be null")
   Integer id,

   @NotEmpty(message = "First name is required")
   String firstName,

   @NotEmpty(message = "Last name is required")
   String lastName,

   @NotNull(message = "Date of birth is required")
   @Past(message = "Date of birth must be a past date")
   LocalDate birthdate,

   @NotNull(message = "Gender is required")
   Gender gender,

   String address,

   @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Phone number must be in the format XXX-XXX-XXXX")
   String phoneNumber


) {}
