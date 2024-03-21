package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common;

public class SaceConflictException extends RuntimeException {
    public SaceConflictException() {
        super("Resource not found exception.");
    }

    public SaceConflictException(String error) {
        super(error);
    }
}
