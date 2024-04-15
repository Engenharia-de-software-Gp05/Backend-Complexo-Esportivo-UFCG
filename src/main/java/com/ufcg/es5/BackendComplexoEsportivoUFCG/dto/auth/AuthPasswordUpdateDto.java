package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthPasswordUpdateDto(
        @NotBlank
        String currentPassword,
        @NotBlank
        String newPassword
) {
}
