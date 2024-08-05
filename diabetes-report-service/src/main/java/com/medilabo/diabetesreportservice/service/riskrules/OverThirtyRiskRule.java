package com.medilabo.diabetesreportservice.service.riskrules;

import com.medilabo.diabetesreportservice.model.RiskLevel;

public class OverThirtyRiskRule implements RiskRule {
    private RiskRule next;

    @Override
    public RiskLevel evaluate(boolean isOverThirty, String gender, int triggerCount) {
        if (isOverThirty) {
            if (triggerCount >= 8) return RiskLevel.EARLY_ONSET;
            else if (triggerCount >= 6) return RiskLevel.IN_DANGER;
            else return RiskLevel.BORDERLINE;
        } else if (next != null) {
            return next.evaluate(isOverThirty, gender, triggerCount);
        }
        return RiskLevel.ERROR;
    }

    @Override
    public void setNext(RiskRule next) {
        this.next = next;
    }
}
