package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.UsuarioException;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.ComplexoEspException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.model.Usuario;

public class UsuarioEmailInvalidoException extends ComplexoEspException {
    public UsuarioEmailInvalidoException() {super("Email Invaido");}
}
