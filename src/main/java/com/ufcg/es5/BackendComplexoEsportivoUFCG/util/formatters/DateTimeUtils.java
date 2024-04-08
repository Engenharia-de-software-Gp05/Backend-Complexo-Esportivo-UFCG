package com.ufcg.es5.BackendComplexoEsportivoUFCG.util.formatters;


import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);


    private DateTimeUtils() {
    }
}