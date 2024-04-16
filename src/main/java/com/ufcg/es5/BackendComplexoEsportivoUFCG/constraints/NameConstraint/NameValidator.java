package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.NameConstraint;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.UtilConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jdk.jshell.execution.Util;

public class NameValidator implements ConstraintValidator<NameConstraint, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (null == name) return false;

        name = name.replaceAll("\\s", "");

        return name.length() > 2 && name.length() < 51 && name.matches("^[\\p{L}]+$");
    }
}
