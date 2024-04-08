package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CourtUpdateDto(
        @NotBlank
        String name,

        @NotNull
        List<String> imagesUrls,

        @NotNull
        CourtAvailabilityStatusEnum courtStatusEnum
) {
}
