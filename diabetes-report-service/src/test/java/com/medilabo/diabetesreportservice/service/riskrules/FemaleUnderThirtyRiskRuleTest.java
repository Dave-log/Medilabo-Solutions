package com.medilabo.diabetesreportservice.service.riskrules;

import com.medilabo.diabetesreportservice.model.RiskLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FemaleUnderThirtyRiskRuleTest {

    private FemaleUnderThirtyRiskRule femaleUnderThirtyRiskRule;

    @BeforeEach
    public void setUp() {
        femaleUnderThirtyRiskRule = new FemaleUnderThirtyRiskRule();
    }

    @Test
    public void testEvaluate_EarlyOnset_WhenTriggersAtLeastSeven() {
        RiskLevel riskLevel = femaleUnderThirtyRiskRule.evaluate(false, "F", 7);
        assertEquals(RiskLevel.EARLY_ONSET, riskLevel);

        riskLevel = femaleUnderThirtyRiskRule.evaluate(false, "F", 8);
        assertEquals(RiskLevel.EARLY_ONSET, riskLevel);
    }

    @Test
    public void testEvaluate_InDanger_WhenTriggersFourToSix() {
        RiskLevel riskLevel = femaleUnderThirtyRiskRule.evaluate(false, "F", 4);
        assertEquals(RiskLevel.IN_DANGER, riskLevel);

        riskLevel = femaleUnderThirtyRiskRule.evaluate(false, "F", 5);
        assertEquals(RiskLevel.IN_DANGER, riskLevel);

        riskLevel = femaleUnderThirtyRiskRule.evaluate(false, "F", 6);
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

        femaleUnderThirtyRiskRule.setNext(nextRule);

        RiskLevel riskLevel = femaleUnderThirtyRiskRule.evaluate(true, "F", 4);  // Âge supérieur à 30 ans
        assertEquals(RiskLevel.BORDERLINE, riskLevel);

        riskLevel = femaleUnderThirtyRiskRule.evaluate(false, "M", 4);  // Genre incorrect
        assertEquals(RiskLevel.BORDERLINE, riskLevel);
    }

    @Test
    public void testEvaluate_Error_WhenNoNextRuleAndCriteriaNotMet() {
        RiskLevel riskLevel = femaleUnderThirtyRiskRule.evaluate(true, "F", 4);
        assertEquals(RiskLevel.ERROR, riskLevel);

        riskLevel = femaleUnderThirtyRiskRule.evaluate(false, "M", 4);
        assertEquals(RiskLevel.ERROR, riskLevel);

        riskLevel = femaleUnderThirtyRiskRule.evaluate(false, "F", 3);
        assertEquals(RiskLevel.ERROR, riskLevel);
    }
}
