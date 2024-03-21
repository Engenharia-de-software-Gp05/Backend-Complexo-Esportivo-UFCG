package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;

import java.util.List;

public record CourtResponseDto(
        String name,
        List<String> imagesUrls,
        CourtAvailabilityStatusEnum courtStatusEnum
) {
}
