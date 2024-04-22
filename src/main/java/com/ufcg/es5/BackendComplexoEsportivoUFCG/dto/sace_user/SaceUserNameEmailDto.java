package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user;


import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.projections.SaceUserNameEmailProjection;

public record SaceUserNameEmailDto(
        String name,
        String email
) {
    public SaceUserNameEmailDto(SaceUserNameEmailProjection p) {
        this(p.getName(), p.getEmail());
    }
}