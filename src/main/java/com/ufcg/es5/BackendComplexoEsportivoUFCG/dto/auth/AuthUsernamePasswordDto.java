package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthUsernamePasswordDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
