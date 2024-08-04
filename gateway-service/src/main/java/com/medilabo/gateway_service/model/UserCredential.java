package com.medilabo.gateway_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserCredential {
    @Id
    private Integer id;
    private String email;

    public UserCredential(String email) {
        this.email = email;
    }
}
