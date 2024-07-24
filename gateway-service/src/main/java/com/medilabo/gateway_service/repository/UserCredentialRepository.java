package com.medilabo.gateway_service.repository;

import com.medilabo.gateway_service.model.UserCredential;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserCredentialRepository extends R2dbcRepository<UserCredential, Integer> {
    Mono<UserCredential> findByEmail(String email);
}
