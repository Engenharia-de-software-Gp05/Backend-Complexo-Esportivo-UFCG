package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.projections.SaceDataProjection;

public record SaceUserDataResponseDto(
        String name,
        String studentId,
        String email,
        String phoneNumber,
        String roleEnums
) {
    public SaceUserDataResponseDto(SaceDataProjection p) {
        this(p.getName(), p.getStudentId(), p.getEmail(), p.getPhoneNumber(), p.getRoleEnums());
    }
}
