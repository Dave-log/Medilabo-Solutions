package com.medilabo.patient_service.model;

import jakarta.persistence.*;
import lombok.Data;

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

    private String birthdate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;
}
