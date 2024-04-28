package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservationSaveDto(
        @NotNull
        Long courtId,
        @NotBlank
        String startDateTime
) {
}
