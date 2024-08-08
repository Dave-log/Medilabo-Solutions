package com.medilabo.diabetesreportservice.service;

import com.medilabo.diabetesreportservice.model.DiabetesReport;
import com.medilabo.diabetesreportservice.model.NoteDTO;
import com.medilabo.diabetesreportservice.model.PatientDTO;
import com.medilabo.diabetesreportservice.model.RiskLevel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for generating diabetes reports based on patient data and notes.
 */
@Service
public class DiabetesReportService {
    private final RiskLevelEvaluator riskLevelEvaluator;

    public DiabetesReportService(RiskLevelEvaluator riskLevelEvaluator) {
        this.riskLevelEvaluator = riskLevelEvaluator;
    }

    /**
     * Generates a diabetes report for a given patient using their details and notes.
     *
     * @param patient the patient details
     * @param notes the list of notes related to the patient
     * @return a {@link DiabetesReport} containing the generated report
     */
    public DiabetesReport generateReport(PatientDTO patient, List<NoteDTO> notes) {
        DiabetesReport diabetesReport = new DiabetesReport();
        diabetesReport.setPatientId(patient.id());
        diabetesReport.setPatientName(patient.firstName() + " " + patient.lastName());
        diabetesReport.setRiskLevel(calculateRiskLevel(patient, notes));

        return diabetesReport;
    }

    /**
     * Calculates the risk level for a patient based on their details and notes.
     *
     * @param patient the patient details
     * @param notes the list of notes related to the patient
     * @return a {@link String} representing the risk level
     */
    private String calculateRiskLevel(PatientDTO patient, List<NoteDTO> notes) {
        boolean isOverThirty = AgeCalculator.isOverThirty(patient.birthdate());
        String gender = patient.gender();
        int triggerCount = TriggerCounter.countTriggers(notes);

        RiskLevel riskLevel = riskLevelEvaluator.evaluateRiskLevel(isOverThirty, gender, triggerCount);

        return riskLevel.getRiskLevel();
    }
}
