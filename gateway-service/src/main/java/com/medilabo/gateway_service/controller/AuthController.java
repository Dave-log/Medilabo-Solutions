package com.medilabo.gateway_service.controller;

import com.medilabo.gateway_service.model.UserCredential;
import com.medilabo.gateway_service.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserCredentialService userCredentialService;

    @Autowired
    public AuthController(UserCredentialService userCredentialService) {
        this.userCredentialService = userCredentialService;
    }

    @GetMapping("/google")
    public Mono<Void> authenticateGoogle(JwtAuthenticationToken token) {
        if (token.isAuthenticated()) {
            String email = (String) token.getTokenAttributes().get("email");
            return userCredentialService.findUser(email)
                    .switchIfEmpty(userCredentialService.saveUser(new UserCredential(email)))
                    .then();
        } else {
            return Mono.error(new RuntimeException("Authentication failed"));
        }
    }
}
