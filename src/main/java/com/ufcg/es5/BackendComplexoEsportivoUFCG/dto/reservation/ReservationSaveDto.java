package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.formatters.DateTimeUtils;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationSaveDto(
        @NotNull
        Long courtId,
        @JsonFormat(timezone = DateTimeUtils.DATE_TIME_PATTERN)
        LocalDateTime startDateTime
) {
}
