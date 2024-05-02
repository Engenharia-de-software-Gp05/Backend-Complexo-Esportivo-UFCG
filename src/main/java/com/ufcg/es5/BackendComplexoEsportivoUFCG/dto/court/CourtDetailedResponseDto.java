package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Court.CourtDetailedProjection;

import java.util.List;

public record CourtDetailedResponseDto(
        String name,
        List<String> imagesUrl,
        CourtAvailabilityStatusEnum courtAvailabilityStatusEnum,
        Long minimumIntervalBetweenReservation
) {
    public CourtDetailedResponseDto(CourtDetailedProjection p) {
        this(p.getName(), p.getImagesUrl(), p.getCourtAvailabilityStatusEnum(), p.getMinimumIntervalBetweenReservation());
    }
}
