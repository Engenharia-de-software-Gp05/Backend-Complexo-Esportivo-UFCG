package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common;

public class SaceInternalErrorServerException extends RuntimeException {
    public SaceInternalErrorServerException() {super("Internal error");}

    public SaceInternalErrorServerException(String message) {super(message);}
}