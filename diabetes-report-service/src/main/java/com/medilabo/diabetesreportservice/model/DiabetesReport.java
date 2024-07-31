package com.medilabo.diabetesreportservice.model;

import lombok.Data;

@Data
public class DiabetesReport {
    private Integer patientId;
    private String patientName;
    private String riskLevel;
}
