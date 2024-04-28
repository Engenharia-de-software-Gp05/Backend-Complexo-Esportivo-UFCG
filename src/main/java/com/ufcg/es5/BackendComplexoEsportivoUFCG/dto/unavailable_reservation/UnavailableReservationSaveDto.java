package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.unavailable_reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.formatters.DateTimeUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UnavailableReservationSaveDto(
        @NotNull
        Long courtId,
        @JsonFormat(pattern = DateTimeUtils.DATE_TIME_PATTERN)
        LocalDateTime startDateTime,
        @NotBlank
        String unavailabilityReason
) {
}
