package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.formatters.DateTimeUtils;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


public record ReservationSaveDto(
        @NotNull
        Long courtId,
        @NotNull
        @DateTimeFormat(pattern = DateTimeUtils.DATE_TIME_PATTERN)
        LocalDateTime startDateTime
) {
}
