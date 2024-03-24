package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CourtSaveDto(
        @NotBlank(message = "name cannot be empty")
        String name,

        @NotNull(message = "images cannot be null")
        List<String> imagesUrls,

        @NotNull(message = "status cannot be null")
        CourtAvailabilityStatusEnum courtStatusEnum
) {
}
