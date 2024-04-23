package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections;

import java.time.LocalDateTime;

public interface UnavailableReservationResponseProjection {
    Long getId();
    LocalDateTime getStartDateTime();
    LocalDateTime getEndDateTime();
}
