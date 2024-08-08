package com.medilabo.diabetesreportservice.service.riskrules;

import com.medilabo.diabetesreportservice.model.RiskLevel;

/**
 * Interface for defining a rule to evaluate the diabetes risk level.
 */
public interface RiskRule {

    /**
     * Evaluates the diabetes risk level based on age, gender, and trigger count.
     *
     * @param isOverThirty boolean indicating if the patient is over thirty years old
     * @param gender the gender of the patient
     * @param triggerCount the number of triggers found in the patient's notes
     * @return the evaluated {@link RiskLevel}
     */
    RiskLevel evaluate(boolean isOverThirty, String gender, int triggerCount);

    /**
     * Sets the next rule in the chain of responsibility.
     *
     * @param next the next {@link RiskRule} in the chain
     */
    void setNext(RiskRule next);
}
