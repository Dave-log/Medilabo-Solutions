package com.medilabo.gateway_service.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
