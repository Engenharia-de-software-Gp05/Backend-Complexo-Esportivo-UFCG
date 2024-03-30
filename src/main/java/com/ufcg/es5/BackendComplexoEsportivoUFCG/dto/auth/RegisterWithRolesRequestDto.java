package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint.EmailConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PhoneNumberConstraint.PhoneNumberConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RegisterWithRolesRequestDto(
        @NotNull @EmailConstraint
        String email,
        @NotNull
        String name,
        @NotNull @PhoneNumberConstraint
        String phoneNumber,
        String studentId,
        @NotNull
        String password,
        @NotNull
        Set<SaceUserRoleEnum> userRoles
) {
}