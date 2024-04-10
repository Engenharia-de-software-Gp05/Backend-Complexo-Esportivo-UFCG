package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.StudentIdConstraint;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PhoneNumberConstraint.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = StudentIdValidator.class)
public @interface StudentIdConstraint {

    String message() default "invalid student id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
