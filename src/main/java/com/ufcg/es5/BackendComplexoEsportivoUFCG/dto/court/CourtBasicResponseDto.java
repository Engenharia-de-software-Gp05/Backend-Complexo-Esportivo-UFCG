package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Court.CourtBasicProjection;

public record CourtBasicResponseDto(
        Long id,
        String name
) {
    public CourtBasicResponseDto(CourtBasicProjection p) {
        this(p.getId(), p.getName());
    }
}
