package com.medilabo.gateway_service.controller;

import com.medilabo.gateway_service.model.UserCredential;
import com.medilabo.gateway_service.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * REST controller for handling authentication-related requests.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handles login requests. Authenticates the user based on the provided JWT token.
     *
     * @param token the JWT authentication token
     * @return a {@link Mono} emitting the {@link ResponseEntity} with a success or failure message
     */
    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(JwtAuthenticationToken token) {
        if (token.isAuthenticated()) {
            String email = token.getTokenAttributes().get("email").toString();
            logger.info("{} is authenticated", email);

            return authService.findUser(email)
                    .map(user -> ResponseEntity.ok("Hello " + email + "!"))
                    .switchIfEmpty(Mono.just(ResponseEntity.status(401).body("User not found. Please register.")));
        }
        return Mono.just(ResponseEntity.status(401).body("Authentication failed"));
    }

    /**
     * Handles registration requests. Registers a new user based on the provided JWT token.
     *
     * @param token the JWT authentication token
     * @return a {@link Mono} emitting the {@link ResponseEntity} with a success or conflict message
     */
    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(JwtAuthenticationToken token) {
        String email = "";
        if (token.isAuthenticated()) {
            email = token.getTokenAttributes().get("email").toString();
            logger.info("{} is authenticated", email);
        }
        return authService.findUser(email)
                .flatMap(existingUser ->
                        Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.")))
                .switchIfEmpty(authService.saveUser(new UserCredential(email))
                        .thenReturn(ResponseEntity.ok("User registered successfully")));
    }
}
