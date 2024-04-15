package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;

import java.time.LocalDateTime;

public record ReservationResponseDto(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Court court
) {
}
