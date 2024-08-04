package com.medilabo.diabetesreportservice.service;

import com.medilabo.diabetesreportservice.model.NoteDTO;
import com.medilabo.diabetesreportservice.model.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DataCollectorService {
    private final WebClient webClient;

    @Autowired
    public DataCollectorService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<NoteDTO>> getPatientNotes(String patientId, String jwtToken) {
        return this.webClient
                .get()
                .uri("/note/api/v1/notes/patient/{id}", patientId)
                .headers(headers -> headers.setBearerAuth(jwtToken))
                .retrieve()
                .bodyToFlux(NoteDTO.class)
                .collectList()
                .doOnError(error -> {
                    System.err.println("Failed to fetch notes: " + error.getMessage());
                });
    }

    public Mono<PatientDTO> getPatientDetails(Integer patientId, String jwtToken) {
        return this.webClient
                .get()
                .uri("/patient/api/v1/patients/{id}", patientId)
                .headers(headers -> headers.setBearerAuth(jwtToken))
                .retrieve()
                .bodyToMono(PatientDTO.class)
                .doOnError(error -> {
                    System.err.println("Failed to fetch patient: " + error.getMessage());
                });
    }
}
