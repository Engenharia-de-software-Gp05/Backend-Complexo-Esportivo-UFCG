package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import jakarta.validation.constraints.NotNull;

public record AuthUsernamePasswordDto(
        @NotNull
        String username,
        @NotNull
        String password
) {
}
