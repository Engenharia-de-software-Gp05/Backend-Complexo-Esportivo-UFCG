package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record AuthRegisterDataWithRolesDto(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String name,
        @NotBlank
        String phoneNumber,
        @NotNull
        Set<SaceUserRoleEnum> userRoles
) {
}