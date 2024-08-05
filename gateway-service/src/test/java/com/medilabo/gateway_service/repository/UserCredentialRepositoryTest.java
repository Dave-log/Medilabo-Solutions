package com.medilabo.gateway_service.repository;

import com.medilabo.gateway_service.GatewayServiceApplicationTests;
import com.medilabo.gateway_service.model.UserCredential;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class UserCredentialRepositoryTest extends GatewayServiceApplicationTests {

    @Autowired
    private UserCredentialRepository credentialRepository;

    @Test
    public void testFindAllUserCredentials() {
        Flux<UserCredential> credentials = credentialRepository.findAll();
        StepVerifier.create(credentials)
                .expectNextCount(3)
                .verifyComplete();
    }
}
