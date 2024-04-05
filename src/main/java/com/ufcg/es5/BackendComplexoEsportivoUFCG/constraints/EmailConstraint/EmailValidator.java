package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && EmailFormat.validateEmailLogical(email);
    }
}
