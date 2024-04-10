package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailStudentConstraint;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.UtilConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailStudentValidator implements ConstraintValidator<EmailStudentConstraint, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (
            !UtilConstraint.isNotBlank(email) ||
            email.contains(" ") ||
            !email.matches("^[A-Za-z.]+@[A-Za-z.]+\\.[A-Za-z.]+$")
        ) {return false;}

        String[] emailArray = email.split("@");

        if (emailArray.length != 2) return false;

        return emailArray[1].equals("estudante.ufcg.edu.br");
    }
}
