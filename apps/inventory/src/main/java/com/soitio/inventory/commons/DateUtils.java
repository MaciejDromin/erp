package com.soitio.inventory.commons;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private static final DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateUtils() {
        throw new AssertionError("Utility class");
    }

    public static LocalDate fromString(String s) {
        return LocalDate.parse(s, LOCAL_DATE_FORMAT);
    }

}
