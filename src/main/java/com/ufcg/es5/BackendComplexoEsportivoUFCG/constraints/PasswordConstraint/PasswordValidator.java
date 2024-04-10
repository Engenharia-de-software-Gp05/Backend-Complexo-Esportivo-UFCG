package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PasswordConstraint;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.UtilConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return UtilConstraint.isNotBlank(password) && !password.contains(" ") &&
                password.matches("^(?=.*[!-~])[\\x21-\\x7E]{8,30}$") && isStrongPassword(password);
    }

    private boolean isStrongPassword(String password) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char ch : password.toCharArray()) {
            if (hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar) {
                return true;
            } else if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowerCase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else {
                hasSpecialChar = true;
            }
        }
        return false;
    }
}
