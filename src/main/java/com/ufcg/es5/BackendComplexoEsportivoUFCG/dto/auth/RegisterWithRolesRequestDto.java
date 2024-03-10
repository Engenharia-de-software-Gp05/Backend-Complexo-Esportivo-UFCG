package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.enums.UserRoleEnum;

import java.util.Set;

public record RegisterWithRolesRequestDto(
        String email,
        String name,
        String phoneNumber,
        String studentId,
        String password,
        Set<UserRoleEnum> userRoles
) {
}
