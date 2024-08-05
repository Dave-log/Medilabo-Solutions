package com.medilabo.diabetesreportservice.service.riskrules;

import com.medilabo.diabetesreportservice.model.RiskLevel;

public class FemaleUnderThirtyRiskRule implements RiskRule {
    private RiskRule next;

    @Override
    public RiskLevel evaluate(boolean isOverThirty, String gender, int triggerCount) {
        if (!isOverThirty && gender.equals("F")) {
            if (triggerCount >= 7) return RiskLevel.EARLY_ONSET;
            else if (triggerCount >= 4) return RiskLevel.IN_DANGER;
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
