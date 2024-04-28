package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.unavailable_reservation;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.UnavailableReservationResponseProjection;

import java.time.LocalDateTime;

public record UnavailableReservationResponseDto(
        Long id,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {
    public UnavailableReservationResponseDto(UnavailableReservationResponseProjection p){
        this(p.getId(), p.getStartDateTime(), p.getEndDateTime());
    }
}
