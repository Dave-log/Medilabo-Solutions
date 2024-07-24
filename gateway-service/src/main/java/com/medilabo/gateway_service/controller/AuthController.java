package com.medilabo.gateway_service.controller;

import com.medilabo.gateway_service.model.AuthRequestDTO;
import com.medilabo.gateway_service.model.UserCredential;
import com.medilabo.gateway_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestBody AuthRequestDTO authRequestDTO) {
        return authService.authenticate(authRequestDTO.getEmail(), authRequestDTO.getPassword());
    }

    @PostMapping("/register")
    public Mono<String> addNewUser(@RequestBody UserCredential user) {
        return authService.saveUser(user).then(Mono.just("User registered successfully"));
    }

    @PostMapping("/token")
    public Mono<String> getToken(@RequestBody AuthRequestDTO authRequestDTO) {
        return authService.generateToken(authRequestDTO.getEmail());
    }

    @GetMapping("/validate")
    public Mono<Boolean> validateToken(@RequestParam("token") String token) {
        return authService.validateToken(token);
    }
}
