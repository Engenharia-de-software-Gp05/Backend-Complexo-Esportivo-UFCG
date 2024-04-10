package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailStudentConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmailStudentValidator.class)
public @interface EmailStudentConstraint {
    String message() default "invalid student email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
