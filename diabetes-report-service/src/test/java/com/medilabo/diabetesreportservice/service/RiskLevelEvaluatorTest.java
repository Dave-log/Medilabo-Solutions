package com.medilabo.diabetesreportservice.service;

import com.medilabo.diabetesreportservice.model.RiskLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RiskLevelEvaluatorTest {

    private RiskLevelEvaluator riskLevelEvaluator;

    @BeforeEach
    public void setUp() {
        riskLevelEvaluator = new RiskLevelEvaluator();
    }

    @Test
    public void testEvaluateRiskLevel_NoneRisk() {
        // Case with less than 2 triggers
        RiskLevel riskLevel = riskLevelEvaluator.evaluateRiskLevel(true, "M", 1);
        assertEquals(RiskLevel.NONE, riskLevel);

        riskLevel = riskLevelEvaluator.evaluateRiskLevel(false, "F", 0);
        assertEquals(RiskLevel.NONE, riskLevel);
    }

    @Test
    public void testEvaluateRiskLevel_BorderlineRisk() {
        // Case with 2-5 triggers and over 30 years old
        RiskLevel riskLevel = riskLevelEvaluator.evaluateRiskLevel(true, "M", 2);
        assertEquals(RiskLevel.BORDERLINE, riskLevel);

        riskLevel = riskLevelEvaluator.evaluateRiskLevel(true, "F", 5);
        assertEquals(RiskLevel.BORDERLINE, riskLevel);
    }

    @Test
    public void testEvaluateRiskLevel_InDangerRisk() {
        // Cases with 6-7 triggers and over 30 years old
        RiskLevel riskLevel = riskLevelEvaluator.evaluateRiskLevel(true, "M", 6);
        assertEquals(RiskLevel.IN_DANGER, riskLevel);

        riskLevel = riskLevelEvaluator.evaluateRiskLevel(true, "F", 7);
        assertEquals(RiskLevel.IN_DANGER, riskLevel);

        // Male under 30 with 3 triggers
        riskLevel = riskLevelEvaluator.evaluateRiskLevel(false, "M", 3);
        assertEquals(RiskLevel.IN_DANGER, riskLevel);

        // Female under 30 with 4-6 triggers
        riskLevel = riskLevelEvaluator.evaluateRiskLevel(false, "F", 4);
        assertEquals(RiskLevel.IN_DANGER, riskLevel);

        riskLevel = riskLevelEvaluator.evaluateRiskLevel(false, "F", 6);
        assertEquals(RiskLevel.IN_DANGER, riskLevel);
    }

    @Test
    public void testEvaluateRiskLevel_EarlyOnsetRisk() {
        // Case with 8+ triggers and over 30 years old
        RiskLevel riskLevel = riskLevelEvaluator.evaluateRiskLevel(true, "M", 8);
        assertEquals(RiskLevel.EARLY_ONSET, riskLevel);

        riskLevel = riskLevelEvaluator.evaluateRiskLevel(true, "F", 9);
        assertEquals(RiskLevel.EARLY_ONSET, riskLevel);

        // Male under 30 with 5+ triggers
        riskLevel = riskLevelEvaluator.evaluateRiskLevel(false, "M", 5);
        assertEquals(RiskLevel.EARLY_ONSET, riskLevel);

        // Female under 30 with 7+ triggers
        riskLevel = riskLevelEvaluator.evaluateRiskLevel(false, "F", 7);
        assertEquals(RiskLevel.EARLY_ONSET, riskLevel);

        riskLevel = riskLevelEvaluator.evaluateRiskLevel(false, "F", 10);
        assertEquals(RiskLevel.EARLY_ONSET, riskLevel);
    }

    @Test
    public void testEvaluateRiskLevel_ErrorScenario() {
        // Scenario where none of the rules apply, should not happen, but test for completeness
        RiskLevel riskLevel = riskLevelEvaluator.evaluateRiskLevel(false, "X", 10);  // Invalid gender
        assertEquals(RiskLevel.ERROR, riskLevel);

        riskLevel = riskLevelEvaluator.evaluateRiskLevel(false, "M", -1);  // Negative triggers, should not happen
        assertEquals(RiskLevel.ERROR, riskLevel);
    }

}
