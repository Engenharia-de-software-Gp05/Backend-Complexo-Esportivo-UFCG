package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.ReservationResponseProjection;

import java.time.LocalDateTime;

public record ReservationResponseDto(
        Long id,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {
    public ReservationResponseDto(ReservationResponseProjection p){
        this(p.getId(), p.getStartDateTime(), p.getEndDateTime());
    }
}
