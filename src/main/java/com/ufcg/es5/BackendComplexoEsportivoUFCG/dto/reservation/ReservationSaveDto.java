package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationSaveDto(
        @NotNull
        Long courtId,
        @NotNull
        LocalDateTime startDateTime,
        @NotNull
        LocalDateTime endDateTime
) {
}
