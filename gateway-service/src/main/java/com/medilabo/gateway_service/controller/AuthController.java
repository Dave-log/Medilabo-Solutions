package com.medilabo.gateway_service.controller;

import com.medilabo.gateway_service.model.AuthRequestDTO;
import com.medilabo.gateway_service.model.UserCredential;
import com.medilabo.gateway_service.service.AuthService;
import com.medilabo.gateway_service.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider, ReactiveAuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody Mono<AuthRequestDTO> authRequestDTO) {
        return authRequestDTO
                .flatMap(login -> this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                login.getEmail(), login.getPassword()))
                        .map(this.jwtTokenProvider::createToken))
                .map(jwt -> {
                   var tokenBody = Map.of("access_token", jwt);
                   return ResponseEntity.ok().body(tokenBody);
                });
    }

    @GetMapping("/oauth2")
    public Mono<ResponseEntity<Map<String, String>>> oauth2Login() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(auth -> {
                    String jwt = jwtTokenProvider.createToken(auth);
                    var tokenBody = Map.of("access_token", jwt);
                    System.out.println("OAuth2 callback token: " + jwt);
                    return ResponseEntity.ok().body(tokenBody);
                });
    }

    @PostMapping("/register")
    public Mono<String> addNewUser(@RequestBody UserCredential user) {
        return authService.saveUser(user).then(Mono.just("User registered successfully"));
    }

    @GetMapping("/validate")
    public Mono<Boolean> validateToken(@RequestParam("token") String token) {
        return authService.validateToken(token);
    }
}
