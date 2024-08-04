package com.medilabo.gateway_service.service;

import com.medilabo.gateway_service.model.UserCredential;
import com.medilabo.gateway_service.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private final UserCredentialRepository repository;

    @Autowired
    public AuthService(UserCredentialRepository repository) {
        this.repository = repository;
    }

    public Mono<UserCredential> findUser(String email) {
        return repository.findByEmail(email);
    }

    public Mono<UserCredential> saveUser(UserCredential user){
        return repository.save(user);
    }
}
