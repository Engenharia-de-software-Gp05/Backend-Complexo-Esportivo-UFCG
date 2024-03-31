package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common;

public class SaceForbiddenException extends RuntimeException {
    public SaceForbiddenException() {super("Request denied");}

    public SaceForbiddenException(String message) {super(message);}
}
