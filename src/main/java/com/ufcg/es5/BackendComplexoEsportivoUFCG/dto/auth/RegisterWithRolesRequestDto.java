package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint.EmailConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PhoneNumberConstraint.PhoneNumberConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RegisterWithRolesRequestDto(
        @NotBlank @EmailConstraint
        String email,
        @NotBlank
        String name,
        @NotBlank @PhoneNumberConstraint
        String phoneNumber,
        String studentId,
        @NotBlank
        String password,
        @NotNull
        Set<SaceUserRoleEnum> userRoles
) {
}
