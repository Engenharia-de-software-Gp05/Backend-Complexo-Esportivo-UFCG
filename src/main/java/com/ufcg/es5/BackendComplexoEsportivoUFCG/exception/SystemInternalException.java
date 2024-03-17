package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception;

public class SystemInternalException extends RuntimeException {
    public SystemInternalException() {
        super("Application internal error.");
    }

    public SystemInternalException(String error) {
        super(error);
    }

}
