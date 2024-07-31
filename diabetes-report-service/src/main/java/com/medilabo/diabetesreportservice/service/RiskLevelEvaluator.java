package com.medilabo.diabetesreportservice.service;

import com.medilabo.diabetesreportservice.model.RiskLevel;

public final class RiskLevelEvaluator {

    public static RiskLevel evaluateRiskLevel(boolean isOverThirty, String gender, int triggerCount) {
        if (triggerCount < 2) {
            return RiskLevel.NONE;
        }

        if (isOverThirty) {
            if (triggerCount >= 8) return RiskLevel.EARLY_ONSET;
            else if (triggerCount >= 6) return RiskLevel.IN_DANGER;
            else return RiskLevel.BORDERLINE;
        }

        if (gender.equals("M")) {
            if (triggerCount >= 5) return RiskLevel.EARLY_ONSET;
            else if (triggerCount >= 3) return RiskLevel.IN_DANGER;
        }

        if (gender.equals("F")) {
            if (triggerCount >= 7) return RiskLevel.EARLY_ONSET;
            else if (triggerCount >= 4) return RiskLevel.IN_DANGER;
        }

        return RiskLevel.ERROR;
    }
}
