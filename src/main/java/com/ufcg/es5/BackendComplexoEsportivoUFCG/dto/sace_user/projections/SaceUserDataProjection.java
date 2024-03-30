package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.projections;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;

import java.util.Set;

public interface SaceUserDataProjection {
    String getName();

    String getStudentId();

    String getEmail();

    String getPhoneNumber();

    Set<SaceUserRoleEnum> getRoleEnums();
}
