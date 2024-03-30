package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint.EmailConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PhoneNumberConstraint.PhoneNumberConstraint;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDto(
        @NotNull
        String email,
        @NotNull
        String name,
        @NotNull
        String phoneNumber,
        @NotNull
        String studentId,
        @NotNull
        String password
) {
}