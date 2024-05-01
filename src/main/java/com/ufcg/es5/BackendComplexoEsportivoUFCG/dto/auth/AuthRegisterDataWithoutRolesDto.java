package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint.EmailConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailStudentConstraint.StudentEmailConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.NameConstraint.NameConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PasswordConstraint.PasswordConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PhoneNumberConstraint.PhoneNumberConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.StudentIdConstraint.StudentIdConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AuthRegisterDataWithoutRolesDto(
        @StudentEmailConstraint
        String email,
        @NameConstraint
        String name,
        @PhoneNumberConstraint
        String phoneNumber,
        @StudentIdConstraint
        String studentId,
        @PasswordConstraint
        String password
) {
}