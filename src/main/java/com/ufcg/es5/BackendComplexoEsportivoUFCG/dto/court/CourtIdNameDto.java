package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Court.CourtIdNameProjection;

public record CourtIdNameDto(
        Long id,
        String name
) {
    public CourtIdNameDto(CourtIdNameProjection p) {
        this(p.getId(), p.getName());
    }
}
