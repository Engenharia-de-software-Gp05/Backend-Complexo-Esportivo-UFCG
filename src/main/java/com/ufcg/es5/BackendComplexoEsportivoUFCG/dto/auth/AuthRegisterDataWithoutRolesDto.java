package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AuthRegisterDataWithoutRolesDto(
        @NotEmpty
        String email,
        @NotEmpty
        String name,
        @NotEmpty
        String phoneNumber,
        @NotEmpty
        String studentId,
        @NotEmpty
        String password
) {
}
