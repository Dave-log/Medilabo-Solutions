package com.medilabo.diabetesreportservice.service.riskrules;

import com.medilabo.diabetesreportservice.model.RiskLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OverThirtyRiskRuleTest {

    private OverThirtyRiskRule overThirtyRiskRule;

    @BeforeEach
    public void setUp() {
        overThirtyRiskRule = new OverThirtyRiskRule();
    }

    @Test
    public void testEvaluate_EarlyOnset_WhenOverThirtyAndTriggersEightOrMore() {
        RiskLevel riskLevel = overThirtyRiskRule.evaluate(true, "M", 8);
        assertEquals(RiskLevel.EARLY_ONSET, riskLevel);

        riskLevel = overThirtyRiskRule.evaluate(true, "F", 9);
        assertEquals(RiskLevel.EARLY_ONSET, riskLevel);
    }

    @Test
    public void testEvaluate_InDanger_WhenOverThirtyAndTriggersBetweenSixAndSeven() {
        RiskLevel riskLevel = overThirtyRiskRule.evaluate(true, "M", 6);
        assertEquals(RiskLevel.IN_DANGER, riskLevel);

        riskLevel = overThirtyRiskRule.evaluate(true, "F", 7);
        assertEquals(RiskLevel.IN_DANGER, riskLevel);
    }

    @Test
    public void testEvaluate_Borderline_WhenOverThirtyAndTriggersBetweenTwoAndFive() {
        RiskLevel riskLevel = overThirtyRiskRule.evaluate(true, "M", 2);
        assertEquals(RiskLevel.BORDERLINE, riskLevel);

        riskLevel = overThirtyRiskRule.evaluate(true, "F", 5);
        assertEquals(RiskLevel.BORDERLINE, riskLevel);
    }

    @Test
    public void testEvaluate_DelegatesToNextRule_WhenUnderThirty() {
        RiskRule nextRule = new RiskRule() {
            @Override
            public RiskLevel evaluate(boolean isOverThirty, String gender, int triggerCount) {
                return RiskLevel.NONE;
            }

            @Override
            public void setNext(RiskRule next) {}
        };

        overThirtyRiskRule.setNext(nextRule);

        RiskLevel riskLevel = overThirtyRiskRule.evaluate(false, "M", 8);
        assertEquals(RiskLevel.NONE, riskLevel);
    }
}
