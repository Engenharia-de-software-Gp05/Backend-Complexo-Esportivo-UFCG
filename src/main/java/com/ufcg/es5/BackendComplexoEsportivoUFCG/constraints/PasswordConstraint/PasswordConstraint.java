package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PasswordConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordConstraint {
    String message() default "Invalid password.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
