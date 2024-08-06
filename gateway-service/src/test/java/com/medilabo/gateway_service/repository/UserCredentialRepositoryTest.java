package com.medilabo.gateway_service.repository;

import com.medilabo.gateway_service.GatewayContextTest;
import com.medilabo.gateway_service.model.UserCredential;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserCredentialRepositoryTest extends GatewayContextTest {

    @Autowired
    private UserCredentialRepository credentialRepository;

    @Test
    public void testFindAllUserCredentials() {
        Flux<UserCredential> credentials = credentialRepository.findAll();
        StepVerifier.create(credentials)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void testSaveUserCredential() {
        UserCredential newUser = new UserCredential();
        newUser.setEmail("newuser@example.com");

        Mono<UserCredential> savedUser = credentialRepository.save(newUser);

        StepVerifier.create(savedUser)
                .assertNext(user -> {
                    assertNotNull(user.getId());
                    assertEquals("newuser@example.com", user.getEmail());
                })
                .verifyComplete();

        StepVerifier.create(credentialRepository.findByEmail("newuser@example.com"))
                .expectNextMatches(user -> user.getEmail().equals("newuser@example.com"))
                .verifyComplete();
    }
}
