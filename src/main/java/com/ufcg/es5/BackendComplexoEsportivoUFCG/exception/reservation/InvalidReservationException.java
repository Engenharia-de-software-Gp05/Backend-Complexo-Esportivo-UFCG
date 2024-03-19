package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.reservation;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.SystemInternalException;

public class InvalidReservationException extends SystemInternalException {
    public InvalidReservationException() {
        super("Invalid reservation.");
    }
}
