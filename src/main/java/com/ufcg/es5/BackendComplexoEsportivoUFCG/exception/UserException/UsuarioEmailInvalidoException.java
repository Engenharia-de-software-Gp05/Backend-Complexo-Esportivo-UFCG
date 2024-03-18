package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.UserException;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.ComplexoEspExceptionBadRequest;

public class UsuarioEmailInvalidoException extends ComplexoEspExceptionBadRequest {
    public UsuarioEmailInvalidoException() {super("Email Invaido");}
}
