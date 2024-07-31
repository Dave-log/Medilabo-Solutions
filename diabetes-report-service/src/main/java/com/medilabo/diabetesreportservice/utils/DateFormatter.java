package com.medilabo.diabetesreportservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateFormatter {

    private static final Logger logger = LoggerFactory.getLogger(DateFormatter.class);

    private static final SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    private static final SimpleDateFormat targetFormat = new SimpleDateFormat("yyy-MM-dd");

    public static String convertToStandardFormat(String dateString) {
        try {
            Date date = originalFormat.parse(dateString);
            return targetFormat.format(date);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
