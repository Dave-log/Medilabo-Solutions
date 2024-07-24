package com.medilabo.gateway_service.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secretKey = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private final long validityInMs = 3600000; // 1h
}
