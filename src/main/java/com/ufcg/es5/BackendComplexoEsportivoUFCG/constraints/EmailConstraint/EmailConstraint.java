package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidadorEmail.class)
public @interface EmailConstraint {
    String message() default "Email tem de ser o institucional da UFCG, exemplo: aluno@estudante.ufcg.edu.br";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
