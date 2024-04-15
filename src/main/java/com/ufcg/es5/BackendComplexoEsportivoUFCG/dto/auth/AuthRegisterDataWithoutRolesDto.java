package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint.EmailConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PhoneNumberConstraint.PhoneNumberConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AuthRegisterDataWithoutRolesDto(
        @EmailConstraint
        String email,
        @NotEmpty
        @Size(min = 8)
        String name,
        @NotEmpty
        String phoneNumber,
        @NotEmpty
        @Size(min = 9, max = 9)
        String studentId,
        @NotEmpty
        @Size(min = 8, max = 32)
        String password
) {
}