package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(
        String email,
        String password
) {
}
