package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common;

public class SaceInternalException extends RuntimeException {
    public SaceInternalException() {super("Internal error");}

    public SaceInternalException(String message) {super(message);}
}
