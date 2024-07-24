package com.medilabo.gateway_service.model;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String email;
    private String password;
}
