package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.UtilConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return EmailFormat.validateEmailLogical(email);
    }
}
