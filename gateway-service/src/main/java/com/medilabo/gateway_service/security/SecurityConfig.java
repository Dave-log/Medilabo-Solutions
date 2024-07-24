package com.medilabo.gateway_service.security;

import com.medilabo.gateway_service.repository.UserCredentialRepository;
import com.medilabo.gateway_service.security.jwt.JwtTokenAuthenticationFilter;
import com.medilabo.gateway_service.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            JwtTokenProvider tokenProvider,
            ReactiveAuthenticationManager authManager) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authenticationManager(authManager)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .cors(cors -> cors.configurationSource(configurationSource()))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/login","/auth/**").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt( new JwtTokenAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.HTTP_BASIC)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .build();
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService(UserCredentialRepository repository) {
        return (email) -> repository.findByEmail(email)
                .map(userCredential -> User
                        .withUsername(userCredential.getEmail())
                        .password(userCredential.getPassword())
                        .authorities(userCredential.getRole())
                        .accountExpired(false)
                        .credentialsExpired(false)
                        .disabled(false)
                        .accountLocked(false)
                        .build());
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:3000");
        corsConfig.addAllowedOrigin("http://localhost:5173");
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }

    @Bean
    public AuthenticationWebFilter jwtAuthenticationWebFilter(ReactiveAuthenticationManager authManager) {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(authManager);
        filter.setSecurityContextRepository(securityContextRepository());
        return filter;
    }

    @Bean
    public ServerSecurityContextRepository securityContextRepository() {
        return new WebSessionServerSecurityContextRepository();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(
            ReactiveUserDetailsService reactiveUserDetailsService,
            PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}