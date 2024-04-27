package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Court;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;

public interface CourtDetailedProjection {
    String getName();
    CourtAvailabilityStatusEnum getCourtAvailabilityStatusEnum();
    Long getMinimumIntervalBetweenReservation();
}
