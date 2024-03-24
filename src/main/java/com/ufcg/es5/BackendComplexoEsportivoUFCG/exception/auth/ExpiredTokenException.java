package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.auth;

public class ExpiredTokenException extends UnauthorizedException{
    public ExpiredTokenException(String message) {
        super(message);
    }
}
