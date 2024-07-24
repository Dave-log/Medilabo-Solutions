package com.medilabo.gateway_service.service;

import com.medilabo.gateway_service.model.UserCredential;
import com.medilabo.gateway_service.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private final UserCredentialRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Autowired
    public AuthService(UserCredentialRepository repository, PasswordEncoder encoder, JwtService jwtService) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public Mono<String> authenticate(String email, String password) {
        return repository.findByEmail(email)
                .filter(user -> encoder.matches(password, user.getPassword()))
                .flatMap(user -> generateToken(user.getEmail()))
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid Credentials")));
    }

    public Mono<UserCredential> saveUser(UserCredential credential){
        credential.setPassword(encoder.encode(credential.getPassword()));
        credential.setRole("USER");
        return repository.save(credential);
    }

    public Mono<String> generateToken(String email) {
        return Mono.just(jwtService.generateToken(email));
    }

    public Mono<Boolean> validateToken(String token) {
        return Mono.just(jwtService.isTokenValid(token));
    }
}
