package com.medilabo.diabetesreportservice.service;

import com.medilabo.diabetesreportservice.model.DiabetesReport;
import com.medilabo.diabetesreportservice.model.NoteDTO;
import com.medilabo.diabetesreportservice.model.PatientDTO;
import com.medilabo.diabetesreportservice.model.RiskLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiabetesReportService {

    public DiabetesReport generateReport(PatientDTO patient, List<NoteDTO> notes) {
        DiabetesReport diabetesReport = new DiabetesReport();
        diabetesReport.setPatientId(patient.id());
        diabetesReport.setPatientName(patient.firstName() + " " + patient.lastName());
        diabetesReport.setRiskLevel(calculateRiskLevel(patient, notes));
        diabetesReport.setDetailedReport(generateDetailedReport());

        return diabetesReport;
    }

    private String generateDetailedReport() {
        // TODO: Implementation
        return "";
    }

    private RiskLevel calculateRiskLevel(PatientDTO patient, List<NoteDTO> notes) {
        // TODO: Implementation
        return RiskLevel.None;
    }
}
