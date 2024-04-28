package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Court.CourtDetailedProjection;

public record CourtDetailedResponseDto(
        String name,
        CourtAvailabilityStatusEnum courtAvailabilityStatusEnum,
        Long minimumIntervalBetweenReservation
) {
    public CourtDetailedResponseDto(CourtDetailedProjection p) {
        this(p.getName(), p.getCourtAvailabilityStatusEnum(), p.getMinimumIntervalBetweenReservation());
    }
}
