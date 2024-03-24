package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common;

import jakarta.persistence.EntityExistsException;

public class SaceConflictException extends EntityExistsException {
    public SaceConflictException() {
        super("Already exists data exception.");
    }

    public SaceConflictException(String error) {
        super(error);
    }
}
