package com.medilabo.diabetesreportservice.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public final class AgeCalculator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    private AgeCalculator() {}

    public static int calculate(String birthdate) {
        if (!DATE_PATTERN.matcher(birthdate).matches()) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd");
        }

        try {
            LocalDate birthdateDate = LocalDate.parse(
                    birthdate,
                    DATE_FORMATTER
            );
            LocalDate currentDate = LocalDate.now();

            return (int) ChronoUnit.YEARS.between(birthdateDate, currentDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date value! " + birthdate, e);
        }

    }

    public static boolean isOverThirty(String birthdate) {
        int age = calculate(birthdate);
        return age > 30;
    }
}
