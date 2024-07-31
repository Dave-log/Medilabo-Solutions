package com.medilabo.diabetesreportservice.model;

import lombok.Getter;

@Getter
public enum RiskLevel {
    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("In Danger"),
    EARLY_ONSET("Early Onset"),
    ERROR("Unable to assess risk level");

    private final String riskLevel;

    RiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
