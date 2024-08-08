package com.medilabo.diabetesreportservice.service;

import com.medilabo.diabetesreportservice.model.RiskLevel;
import com.medilabo.diabetesreportservice.service.riskrules.*;
import org.springframework.stereotype.Service;

/**
 * Service for evaluating the risk level of diabetes based on various rules.
 */
@Service
public final class RiskLevelEvaluator {
    private final RiskRule chain;

    /**
     * Constructs a new RiskLevelEvaluator and sets up the chain of responsibility for risk rules.
     */
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

    /**
     * Evaluates the risk level based on the patient's age, gender, and the count of triggers found in notes.
     *
     * @param isOverThirty boolean indicating if the patient is over thirty years old
     * @param gender the gender of the patient
     * @param triggerCount the number of triggers found in the patient's notes
     * @return a {@link RiskLevel} representing the evaluated risk level
     */
    public RiskLevel evaluateRiskLevel(boolean isOverThirty, String gender, int triggerCount) {
        return this.chain.evaluate(isOverThirty, gender, triggerCount);
    }
}
