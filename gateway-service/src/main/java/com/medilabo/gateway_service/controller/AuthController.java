package com.medilabo.gateway_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    @PostMapping("/login")
    public Mono<String> login() {
        return Mono.just("Login successful");
    }
}
