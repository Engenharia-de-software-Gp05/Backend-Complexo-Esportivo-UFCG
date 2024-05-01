package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PasswordConstraint;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.UtilConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!-/:-@\\[-`{-~])[A-Za-z\\d!-/:-@\\[-`{-~]{8,30}$");

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }
}