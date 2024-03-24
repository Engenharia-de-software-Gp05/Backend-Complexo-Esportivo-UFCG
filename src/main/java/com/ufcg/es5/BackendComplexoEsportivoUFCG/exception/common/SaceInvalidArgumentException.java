package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common;

public class SaceInvalidArgumentException extends RuntimeException {
    public SaceInvalidArgumentException() {super("Invalid argument error.");}

    public SaceInvalidArgumentException(String error) {super(error);}

}
