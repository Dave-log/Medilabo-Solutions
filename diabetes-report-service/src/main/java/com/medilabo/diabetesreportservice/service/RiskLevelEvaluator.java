package com.medilabo.diabetesreportservice.service;

import com.medilabo.diabetesreportservice.model.RiskLevel;
import com.medilabo.diabetesreportservice.service.riskrules.*;
import org.springframework.stereotype.Service;

@Service
public final class RiskLevelEvaluator {
    private final RiskRule chain;

    public RiskLevelEvaluator() {
        RiskRule noneRiskRule = new NoneRiskRule();
        RiskRule overThirtyRiskRule = new OverThirtyRiskRule();
        RiskRule maleUnderThirtyRiskRule = new MaleUnderThirtyRiskRule();
        RiskRule femaleUnderThirtyRiskRule = new FemaleUnderThirtyRiskRule();

        noneRiskRule.setNext(overThirtyRiskRule);
        overThirtyRiskRule.setNext(maleUnderThirtyRiskRule);
        maleUnderThirtyRiskRule.setNext(femaleUnderThirtyRiskRule);

        this.chain = noneRiskRule;
    }

    public RiskLevel evaluateRiskLevel(boolean isOverThirty, String gender, int triggerCount) {
        return this.chain.evaluate(isOverThirty, gender, triggerCount);
    }
}
