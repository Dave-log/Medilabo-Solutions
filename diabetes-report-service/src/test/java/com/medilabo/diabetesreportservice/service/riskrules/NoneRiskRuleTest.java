package com.medilabo.diabetesreportservice.service.riskrules;

import com.medilabo.diabetesreportservice.model.RiskLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoneRiskRuleTest {

    private NoneRiskRule noneRiskRule;

    @BeforeEach
    public void setUp() {
        noneRiskRule = new NoneRiskRule();
    }

    @Test
    public void testEvaluate_None_WhenTriggersLessThanTwo() {
        RiskLevel riskLevel = noneRiskRule.evaluate(false, "M", 0);
        assertEquals(RiskLevel.NONE, riskLevel);

        riskLevel = noneRiskRule.evaluate(false, "F", 1);
        assertEquals(RiskLevel.NONE, riskLevel);
    }

    @Test
    public void testEvaluate_DelegatesToNextRule_WhenTriggersTwoOrMore() {
        RiskRule nextRule = new RiskRule() {
            @Override
            public RiskLevel evaluate(boolean isOverThirty, String gender, int triggerCount) {
                return RiskLevel.BORDERLINE;
            }

            @Override
            public void setNext(RiskRule next) {}
        };

        noneRiskRule.setNext(nextRule);

        RiskLevel riskLevel = noneRiskRule.evaluate(false, "M", 2);
        assertEquals(RiskLevel.BORDERLINE, riskLevel);

        riskLevel = noneRiskRule.evaluate(false, "F", 5);
        assertEquals(RiskLevel.BORDERLINE, riskLevel);
    }

    @Test
    public void testEvaluate_Error_WhenNoNextRuleAndTriggersTwoOrMore() {
        RiskLevel riskLevel = noneRiskRule.evaluate(false, "M", 2);
        assertEquals(RiskLevel.ERROR, riskLevel);

        riskLevel = noneRiskRule.evaluate(false, "F", 3);
        assertEquals(RiskLevel.ERROR, riskLevel);
    }

}
