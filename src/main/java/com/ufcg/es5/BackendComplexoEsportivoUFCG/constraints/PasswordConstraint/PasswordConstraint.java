package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PasswordConstraint;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.NameConstraint.NameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordConstraint {
    String message() default "password is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
