package com.medilabo.gateway_service.service;

import com.medilabo.gateway_service.model.UserCredential;
import com.medilabo.gateway_service.repository.UserCredentialRepository;
import com.medilabo.gateway_service.security.jwt.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Service
public class AuthService {

    private final UserCredentialRepository repository;
    private final PasswordEncoder encoder;
    private final JwtProperties jwtProperties;

    @Autowired
    public AuthService(UserCredentialRepository repository, PasswordEncoder encoder, JwtProperties jwtProperties) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtProperties = jwtProperties;
    }

    public Mono<UserCredential> saveUser(UserCredential credential){
        credential.setPassword(encoder.encode(credential.getPassword()));
        credential.setRole("USER");
        return repository.save(credential);
    }

    public Mono<Boolean> validateToken(String token) {
        return Mono.just(isTokenValid(token));
    }

    private boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
