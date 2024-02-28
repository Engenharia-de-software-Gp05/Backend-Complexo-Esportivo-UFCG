package com.ufcg.es5.BackendComplexoEsportivoUFCG.constraints.EmailConstraint;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.ValidacaoUsuario;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorEmail implements ConstraintValidator<EmailConstraint, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {return ValidacaoUsuario.validarEmail(email);}
}
