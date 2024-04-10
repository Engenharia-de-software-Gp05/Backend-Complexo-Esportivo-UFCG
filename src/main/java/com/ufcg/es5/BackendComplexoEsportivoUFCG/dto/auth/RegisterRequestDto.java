package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint.EmailConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailStudentConstraint.EmailStudentConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.NameConstraint.NameConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PasswordConstraint.PasswordConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PhoneNumberConstraint.PhoneNumberConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.StudentIdConstraint.StudentIdConstraint;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDto(
        @NotNull @EmailStudentConstraint
        String email,
        @NotNull @NameConstraint
        String name,
        @NotNull @PhoneNumberConstraint
        String phoneNumber,
        @NotNull @StudentIdConstraint
        String studentId,
        @NotNull @PasswordConstraint
        String password
) {
}