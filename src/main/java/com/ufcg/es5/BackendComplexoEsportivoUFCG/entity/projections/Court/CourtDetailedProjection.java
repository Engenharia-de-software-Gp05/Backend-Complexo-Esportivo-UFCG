package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Court;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;

import java.util.List;

public interface CourtDetailedProjection {
    Long getId();

    String getName();

    List<String> getImagesUrl();

    CourtAvailabilityStatusEnum getCourtAvailabilityStatusEnum();

    Long getMinimumIntervalBetweenReservation();
}
