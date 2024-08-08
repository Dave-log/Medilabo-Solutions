package com.medilabo.diabetesreportservice.controller;

import com.medilabo.diabetesreportservice.model.DiabetesReport;
import com.medilabo.diabetesreportservice.model.NoteDTO;
import com.medilabo.diabetesreportservice.model.PatientDTO;
import com.medilabo.diabetesreportservice.service.DataCollectorService;
import com.medilabo.diabetesreportservice.service.DiabetesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Rest controller for handling diabetes reports.
 */
@RestController
@RequestMapping("/api/v1/reports")
public class DiabetesReportController {
    private final DataCollectorService dataCollectorService;
    private final DiabetesReportService diabetesReportService;

    @Autowired
    public DiabetesReportController(DataCollectorService dataCollectorService, DiabetesReportService diabetesReportService) {
        this.dataCollectorService = dataCollectorService;
        this.diabetesReportService = diabetesReportService;
    }

    /**
     * Retrieves a diabetes report for a specific patient.
     *
     * @param patientId the ID of the patient
     * @param exchange the server web exchange for accessing request headers
     * @return a {@link Mono} emitting the {@link ResponseEntity} containing the diabetes report or a not found status
     */
    @GetMapping("/{patientId}")
    public Mono<ResponseEntity<DiabetesReport>> getDiabetesReport(@PathVariable Integer patientId, ServerWebExchange exchange) {
        String jwtToken = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.notFound().build());
        }

        jwtToken = jwtToken.substring(7);

        Mono<List<NoteDTO>> patientNotes = dataCollectorService.getPatientNotes(Integer.toString(patientId), jwtToken);
        Mono<PatientDTO> patientDetails = dataCollectorService.getPatientDetails(patientId, jwtToken);

        return Mono.zip(patientDetails, patientNotes)
                .map(tuple -> {
                    PatientDTO patient = tuple.getT1();
                    List<NoteDTO> notes = tuple.getT2();
                    DiabetesReport diabetesReport = diabetesReportService.generateReport(patient, notes);
                    return ResponseEntity.ok(diabetesReport);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
