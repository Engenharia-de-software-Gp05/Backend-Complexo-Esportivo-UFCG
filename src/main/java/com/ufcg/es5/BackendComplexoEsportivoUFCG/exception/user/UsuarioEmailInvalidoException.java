package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.user;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.SystemInternalException;

public class UsuarioEmailInvalidoException extends SystemInternalException {
    public UsuarioEmailInvalidoException() {
        super("Email Invaido");
    }
}
