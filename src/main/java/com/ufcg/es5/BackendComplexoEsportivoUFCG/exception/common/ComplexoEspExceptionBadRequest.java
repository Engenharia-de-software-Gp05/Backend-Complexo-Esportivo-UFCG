package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common;

public class ComplexoEspExceptionBadRequest extends RuntimeException {
    public ComplexoEspExceptionBadRequest() {super("Erro imprevisto na aplicação!");}

    public ComplexoEspExceptionBadRequest(String error) {super(error);}

}
