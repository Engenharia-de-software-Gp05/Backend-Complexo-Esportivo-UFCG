package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common;

public class InternalException extends RuntimeException {
    public InternalException() {super("Internal error");}

    public InternalException(String message) {super(message);}
}
