package com.soitio.commons.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private static final DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateUtils() {
        throw new AssertionError("Utility class");
    }

    public static LocalDate localDateFromString(String s) {
        return LocalDate.parse(s, LOCAL_DATE_FORMAT);
    }

    public static LocalDateTime localDateTimeFromString(String s) {
        return LocalDateTime.parse(s, LOCAL_DATE_TIME_FORMAT);
    }

}
