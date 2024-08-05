package com.medilabo.diabetesreportservice.service.riskrules;

import com.medilabo.diabetesreportservice.model.RiskLevel;

public interface RiskRule {
    RiskLevel evaluate(boolean isOverThirty, String gender, int triggerCount);
    void setNext(RiskRule next);
}
