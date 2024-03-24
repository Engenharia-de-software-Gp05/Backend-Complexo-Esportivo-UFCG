package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.CourtException;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceInvalidArgumentException;

public class InvalidReservationException extends SaceInvalidArgumentException {
    public InvalidReservationException() {super("Invalid reservation.");}
}