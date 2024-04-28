package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Reservation;

import java.time.LocalDateTime;

public interface ReservationResponseProjection {
    Long getId();
    LocalDateTime getStartDateTime();
    LocalDateTime getEndDateTime();
}
