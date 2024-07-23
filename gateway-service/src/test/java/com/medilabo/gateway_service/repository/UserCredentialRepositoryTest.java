package com.medilabo.gateway_service.repository;

import com.medilabo.gateway_service.GatewayServiceApplicationTests;
import com.medilabo.gateway_service.model.UserCredential;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
public class UserCredentialRepositoryTest extends GatewayServiceApplicationTests {

    @Autowired
    private UserCredentialRepository credentialRepository;

    @Test
    public void testFindAllUserCredentials() {
        List<UserCredential> credentials = credentialRepository.findAll();
        assertEquals(credentials.size(), 3);

        UserCredential userCredential = credentials.getFirst();
        assertEquals("johndoe@example.com", userCredential.getEmail());
    }
}
