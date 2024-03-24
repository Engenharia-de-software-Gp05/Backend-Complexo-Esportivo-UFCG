package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common;

public class SaceInvalidArgumentException extends IllegalArgumentException {
    public SaceInvalidArgumentException() {super("Invalid argument exception.");}

    public SaceInvalidArgumentException(String error) {super(error);}

}
