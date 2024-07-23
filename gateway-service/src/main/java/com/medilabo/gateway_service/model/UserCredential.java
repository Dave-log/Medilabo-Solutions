package com.medilabo.gateway_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique=true)
    private String username;
    private String password;
}
