package com.medilabo.diabetesreportservice.service;

import com.medilabo.diabetesreportservice.model.DiabetesReport;
import com.medilabo.diabetesreportservice.model.NoteDTO;
import com.medilabo.diabetesreportservice.model.PatientDTO;
import com.medilabo.diabetesreportservice.model.RiskLevel;
import com.medilabo.diabetesreportservice.utils.AgeCalculator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiabetesReportService {

    public DiabetesReport generateReport(PatientDTO patient, List<NoteDTO> notes) {
        DiabetesReport diabetesReport = new DiabetesReport();
        diabetesReport.setPatientId(patient.id());
        diabetesReport.setPatientName(patient.firstName() + " " + patient.lastName());
        diabetesReport.setRiskLevel(calculateRiskLevel(patient, notes));

        return diabetesReport;
    }

    private String calculateRiskLevel(PatientDTO patient, List<NoteDTO> notes) {
        boolean isOverThirty = AgeCalculator.isOverThirty(patient.birthdate().toString());
        String gender = patient.gender();
        int triggerCount = TriggerCounter.countUniqueTriggers(notes);

        RiskLevel riskLevel = RiskLevelAssessor.assessRiskLevel(isOverThirty, gender, triggerCount);
        return riskLevel.getRiskLevel();
    }
}
