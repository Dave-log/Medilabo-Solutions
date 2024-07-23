package com.medilabo.gateway_service.service;

import com.medilabo.gateway_service.model.UserCredential;
import com.medilabo.gateway_service.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserCredentialRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthService(UserCredentialRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public String saveUser(UserCredential credential){
        credential.setPassword(encoder.encode(credential.getPassword()));
        repository.save(credential);
        return credential.getUsername() + " has been saved";
    }
}
