package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception;

public class ComplexoEspException extends RuntimeException {
    public ComplexoEspException() {super("Erro imprevisto na aplicação!");}

    public ComplexoEspException(String error) {super(error);}

}
