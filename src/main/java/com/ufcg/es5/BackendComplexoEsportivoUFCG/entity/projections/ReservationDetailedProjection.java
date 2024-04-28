package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ReservationDetailedProjection {

    Long getId();

    String getCourtName();

    Collection<String> getCourtImageUrls();

    String getUserName();

    LocalDateTime getStartDateTime();

    LocalDateTime getEndDateTime();
}
