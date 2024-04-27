package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Reservation;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ReservationDetailedProjection {

    String getCourtName();

    Collection<String> getCourtImageUrls();

    String getUserName();

    LocalDateTime getStartDateTime();

    LocalDateTime getEndDateTime();
}
