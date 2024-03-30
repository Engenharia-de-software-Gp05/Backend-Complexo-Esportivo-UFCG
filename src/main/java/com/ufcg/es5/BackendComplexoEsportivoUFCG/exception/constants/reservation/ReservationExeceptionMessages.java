package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.reservation;

public class ReservationExeceptionMessages {

    public static final String RESERVATION_WITH_ID_NOT_FOUND = "Reservation with id '%s' not found.";
    public static final String RESERVATION_NOT_BELONGS_TO_USER = "Reservation with id '%s' does not belong to user '%s'.";
    public static final String RESERVATION_LIMIT_EXCEEDED_IN_COURT_BY_USER = "Reservation limit exceeded for court '%s' by user '%s'.";
    public ReservationExeceptionMessages(){}
}
