package com.medilabo.diabetesreportservice.service.riskrules;

import com.medilabo.diabetesreportservice.model.RiskLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaleUnderThirtyRiskRuleTest {

    private MaleUnderThirtyRiskRule maleUnderThirtyRiskRule;

    @BeforeEach
    public void setUp() {
        maleUnderThirtyRiskRule = new MaleUnderThirtyRiskRule();
    }

    @Test
    public void testEvaluate_EarlyOnset_WhenTriggersAtLeastFive() {
        RiskLevel riskLevel = maleUnderThirtyRiskRule.evaluate(false, "M", 5);
        assertEquals(RiskLevel.EARLY_ONSET, riskLevel);

        riskLevel = maleUnderThirtyRiskRule.evaluate(false, "M", 6);
        assertEquals(RiskLevel.EARLY_ONSET, riskLevel);
    }

    @Test
    public void testEvaluate_InDanger_WhenTriggersThreeOrFour() {
        RiskLevel riskLevel = maleUnderThirtyRiskRule.evaluate(false, "M", 3);
        assertEquals(RiskLevel.IN_DANGER, riskLevel);

        riskLevel = maleUnderThirtyRiskRule.evaluate(false, "M", 4);
        assertEquals(RiskLevel.IN_DANGER, riskLevel);
    }

    @Test
    public void testEvaluate_DelegatesToNextRule_WhenCriteriaNotMet() {
        RiskRule nextRule = new RiskRule() {
            @Override
            public RiskLevel evaluate(boolean isOverThirty, String gender, int triggerCount) {
                return RiskLevel.BORDERLINE;
            }

            @Override
            public void setNext(RiskRule next) {}
        };

        maleUnderThirtyRiskRule.setNext(nextRule);

        RiskLevel riskLevel = maleUnderThirtyRiskRule.evaluate(true, "M", 4);
        assertEquals(RiskLevel.BORDERLINE, riskLevel);

        riskLevel = maleUnderThirtyRiskRule.evaluate(false, "F", 3);
        assertEquals(RiskLevel.BORDERLINE, riskLevel);
    }

    @Test
    public void testEvaluate_Error_WhenNoNextRuleAndCriteriaNotMet() {
        RiskLevel riskLevel = maleUnderThirtyRiskRule.evaluate(true, "M", 4);
        assertEquals(RiskLevel.ERROR, riskLevel);

        riskLevel = maleUnderThirtyRiskRule.evaluate(false, "F", 4);
        assertEquals(RiskLevel.ERROR, riskLevel);

        riskLevel = maleUnderThirtyRiskRule.evaluate(false, "M", 2);
        assertEquals(RiskLevel.ERROR, riskLevel);
    }

}
