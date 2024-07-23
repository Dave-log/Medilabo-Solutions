package com.medilabo.gateway_service.repository;

import com.medilabo.gateway_service.model.UserCredential;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends ReactiveCrudRepository<UserCredential, Integer> {}
