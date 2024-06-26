package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailStudentConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = StudentEmailValidator.class)
public @interface StudentEmailConstraint {
    String message() default "Invalid student email.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
