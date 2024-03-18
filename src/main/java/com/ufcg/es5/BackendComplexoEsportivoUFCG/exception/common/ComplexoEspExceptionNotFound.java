package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common;

public class ComplexoEspExceptionNotFound extends RuntimeException {
    public ComplexoEspExceptionNotFound() {super("Erro imprevisto na aplicação!");}

    public ComplexoEspExceptionNotFound(String error) {super(error);}
}
