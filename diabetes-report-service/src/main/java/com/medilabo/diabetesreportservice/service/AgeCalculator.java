package com.medilabo.diabetesreportservice.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class AgeCalculator {

    private AgeCalculator() {}

    public static int calculate(LocalDate birthdate) {
        LocalDate currentDate = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(birthdate, currentDate);
    }

    public static boolean isOverThirty(LocalDate birthdate) {
        int age = calculate(birthdate);
        return age > 30;
    }
}
