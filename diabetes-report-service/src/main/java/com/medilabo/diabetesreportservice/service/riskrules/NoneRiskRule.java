package com.medilabo.diabetesreportservice.service.riskrules;

import com.medilabo.diabetesreportservice.model.RiskLevel;

public class NoneRiskRule implements RiskRule {
    private RiskRule next;

    @Override
    public RiskLevel evaluate(boolean isOverThirty, String gender, int triggerCount) {
        if (triggerCount < 2) {
            return RiskLevel.NONE;
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
