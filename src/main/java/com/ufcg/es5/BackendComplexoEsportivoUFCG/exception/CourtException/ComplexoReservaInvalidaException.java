package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.CourtException;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.ComplexoEspExceptionBadRequest;

public class ComplexoReservaInvalidaException extends ComplexoEspExceptionBadRequest {
    public ComplexoReservaInvalidaException() {super("Reserva Invalida");}
}
