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
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public DataCollectorService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<List<NoteDTO>> getPatientNotes(String patientId) {
        WebClient webClient = webClientBuilder.build();
        String notesServiceUrl = "lb://notes-service/api/v1/notes/patient/" + patientId;

        return webClient.get()
                .uri(notesServiceUrl)
                .retrieve()
                .bodyToFlux(NoteDTO.class)
                .collectList();
    }

    public Mono<PatientDTO> getPatientDetails(Integer patientId) {
        WebClient webClient = webClientBuilder.build();
        String patientServiceUrl = "lb://patient-service/api/v1/patients/" + patientId;

        return webClient.get()
                .uri(patientServiceUrl)
                .retrieve()
                .bodyToMono(PatientDTO.class);
    }
}
