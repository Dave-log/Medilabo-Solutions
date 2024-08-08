package com.medilabo.diabetesreportservice.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for calculating age and age-related conditions.
 */
public final class AgeCalculator {

    private AgeCalculator() {}

    /**
     * Calculates the age of a person given their birthdate.
     *
     * @param birthdate the birthdate of the person
     * @return the age in years
     */
    public static int calculate(LocalDate birthdate) {
        LocalDate currentDate = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(birthdate, currentDate);
    }

    /**
     * Determines if a person is over thirty years old given their birthdate.
     *
     * @param birthdate the birthdate of the person
     * @return true if the person is over thirty, false otherwise
     */
    public static boolean isOverThirty(LocalDate birthdate) {
        int age = calculate(birthdate);
        return age > 30;
    }
}
