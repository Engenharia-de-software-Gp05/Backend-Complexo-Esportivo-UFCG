package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common;

public class SaceResourceNotFoundException extends RuntimeException {
    public SaceResourceNotFoundException() {super("Resource not found exception.");}

    public SaceResourceNotFoundException(String error) {super(error);}
}
