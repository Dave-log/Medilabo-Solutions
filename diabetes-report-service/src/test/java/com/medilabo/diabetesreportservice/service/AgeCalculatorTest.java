package com.medilabo.diabetesreportservice.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class AgeCalculatorTest {

    @Test
    public void testCalculateAge_ValidDate() {
        String birthdate = "1990-01-01";
        int expectedAge = LocalDate.now().getYear() - 1990;
        assertEquals(expectedAge, AgeCalculator.calculate(birthdate));
    }

    @Test
    public void testCalculateAge_BirthdateToday() {
        String birthdate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertEquals(0, AgeCalculator.calculate(birthdate));
    }

    @Test
    public void testCalculateAge_BirthdateTomorrow() {
        String birthdate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertEquals(0, AgeCalculator.calculate(birthdate));
    }

    @Test
    public void testCalculateAge_InvalidDateFormat() {
        String invalidBirthdate = "01-01-1990";
        assertThrows(IllegalArgumentException.class, () -> {
            AgeCalculator.calculate(invalidBirthdate);
        });
    }

    @Test
    public void testIsOverThirty_True() {
        String birthdate = "1990-01-01";
        assertTrue(AgeCalculator.isOverThirty(birthdate));
    }

    @Test
    public void testIsOverThirty_ExactlyThirty() {
        String birthdate = LocalDate.now().minusYears(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertFalse(AgeCalculator.isOverThirty(birthdate));
    }

    @Test
    public void testIsOverThirty_False() {
        String birthdate = "2000-01-01";
        assertFalse(AgeCalculator.isOverThirty(birthdate));
    }
}
