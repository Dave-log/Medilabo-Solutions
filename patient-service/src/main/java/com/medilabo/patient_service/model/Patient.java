package com.medilabo.patient_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "firstname")
    private String firstName;

    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;
}
