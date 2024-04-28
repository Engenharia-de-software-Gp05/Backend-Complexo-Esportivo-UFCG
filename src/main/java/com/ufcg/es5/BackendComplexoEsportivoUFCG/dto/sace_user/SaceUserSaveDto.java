package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailStudentConstraint.StudentEmailConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.NameConstraint.NameConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PasswordConstraint.PasswordConstraint;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PhoneNumberConstraint.PhoneNumberConstraint;

public record SaceUserSaveDto(
        @NameConstraint
        String name,
        @StudentEmailConstraint
        String email,
        @PhoneNumberConstraint
        String phoneNumber,
        @PasswordConstraint
        String password
) {
}
