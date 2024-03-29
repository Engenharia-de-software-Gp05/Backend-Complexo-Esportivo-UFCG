package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint.EmailConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PhoneNumberConstraint.PhoneNumberConstraint;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(
        @NotBlank @EmailConstraint
        String email,
        @NotBlank
        String name,
        @NotBlank @PhoneNumberConstraint
        String phoneNumber,
        @NotBlank
        String studentId,
        @NotBlank
        String password
) {
}
