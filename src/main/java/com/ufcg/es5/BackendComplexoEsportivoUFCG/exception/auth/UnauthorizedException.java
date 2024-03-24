package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.auth;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("User is not authenticated.");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
