package com.medilabo.gateway_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "users")
public class UserCredential {
    @Id
    private Integer id;
    private String email;
    private String password;
    private String role;
}
