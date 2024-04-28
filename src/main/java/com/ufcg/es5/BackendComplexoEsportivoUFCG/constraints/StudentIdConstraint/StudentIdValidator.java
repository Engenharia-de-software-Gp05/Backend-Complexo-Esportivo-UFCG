package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.StudentIdConstraint;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.UtilConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StudentIdValidator implements ConstraintValidator<StudentIdConstraint, String> {
    @Override
    public boolean isValid(String studentId, ConstraintValidatorContext constraintValidatorContext) {
        return UtilConstraint.isNotBlank(studentId) && !studentId.contains(" ") && studentId.length() == 9 && studentId.matches("^[0-9]+$");
    }
}
