package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourtSaveDto(
        @NotBlank(message = "Court name cannot be empty")
        String name,
        @NotNull(message = "reservation duration cannot be null") @Min(1L)
        Long reservationDuration,
        @NotNull(message = "minimum interval cannot be null") @Min(0L)
        Long minimumIntervalBetweenReservation
) {
}
