package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

public record LoginRequestDto(
        String email,
        String password
) {
}
