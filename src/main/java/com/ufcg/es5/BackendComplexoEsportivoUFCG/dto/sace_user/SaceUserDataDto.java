package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.projections.SaceUserDataProjection;

import java.util.Set;

public record SaceUserDataDto(
        String name,
        String studentId,
        String email,
        String phoneNumber,
        Set<SaceUserRoleEnum> roleEnums
) {
    public SaceUserDataDto(SaceUserDataProjection p) {
        this(p.getName(), p.getStudentId(), p.getEmail(), p.getPhoneNumber(), p.getRoleEnums());
    }
}
