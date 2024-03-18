package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common;

public class ComplexoEspExceptionConflit extends RuntimeException {
    public ComplexoEspExceptionConflit() {
        super("Conflito na aplicação!");
    }

    public ComplexoEspExceptionConflit(String error) {
        super(error);
    }
}
