package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.PhoneNumberConstraint;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumberConstraint {

    String message() default "invalid phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

