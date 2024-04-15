package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common;

import jakarta.persistence.EntityNotFoundException;

public class SaceResourceNotFoundException extends EntityNotFoundException {
    public SaceResourceNotFoundException() {super("Resource not found exception.");}

    public SaceResourceNotFoundException(String error) {super(error);}
}
