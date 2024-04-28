package com.ufcg.es5.BackendComplexoEsportivoUFCG.util.formatters;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceInvalidArgumentException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);


    private DateTimeUtils() {
    }

    public static LocalDateTime toDatetime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            throw new SaceInvalidArgumentException("Erro tentando transformar localDateTime" + e.getMessage());
        }
    }
}