package com.medilabo.diabetesreportservice.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AgeCalculatorTest {

    @Test
    public void testCalculateAge_ValidDate() {
        LocalDate birthdate = LocalDate.parse("1990-01-01");
        int expectedAge = LocalDate.now().getYear() - 1990;
        assertEquals(expectedAge, AgeCalculator.calculate(birthdate));
    }

    @Test
    public void testCalculateAge_BirthdateToday() {
        LocalDate birthdate = LocalDate.now();
        assertEquals(0, AgeCalculator.calculate(birthdate));
    }

    @Test
    public void testCalculateAge_BirthdateTomorrow() {
        LocalDate birthdate = LocalDate.now().plusDays(1);
        assertEquals(0, AgeCalculator.calculate(birthdate));
    }

    @Test
    public void testCalculateAge_InvalidDateFormat() {
        LocalDate invalidBirthdate = LocalDate.parse("01-01-1990");
        assertThrows(IllegalArgumentException.class, () -> {
            AgeCalculator.calculate(invalidBirthdate);
        });
    }

    @Test
    public void testIsOverThirty_True() {
        LocalDate birthdate = LocalDate.parse("1990-01-01");
        assertTrue(AgeCalculator.isOverThirty(birthdate));
    }

    @Test
    public void testIsOverThirty_ExactlyThirty() {
        LocalDate birthdate = LocalDate.now().minusYears(30);
        assertFalse(AgeCalculator.isOverThirty(birthdate));
    }

    @Test
    public void testIsOverThirty_False() {
        LocalDate birthdate = LocalDate.parse("2000-01-01");
        assertFalse(AgeCalculator.isOverThirty(birthdate));
    }
}
