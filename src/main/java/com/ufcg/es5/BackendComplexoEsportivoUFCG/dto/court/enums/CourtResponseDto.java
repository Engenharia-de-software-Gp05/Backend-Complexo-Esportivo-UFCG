package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CourtResponseDto(
        String name,
        List<String> imagesUrls,
        CourtStatusEnum courtStatusEnum
) {
}
