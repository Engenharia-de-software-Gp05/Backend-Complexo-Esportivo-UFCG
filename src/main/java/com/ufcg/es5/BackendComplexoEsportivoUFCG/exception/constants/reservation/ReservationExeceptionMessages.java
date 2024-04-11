package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.reservation;

public class ReservationExeceptionMessages {

    public static final String RESERVATION_WITH_ID_NOT_FOUND = "Reservation with id '%s' not found.";
    public static final String RESERVATION_LIMIT_EXCEEDED_IN_COURT_BY_USER = "Reservation limit exceeded for court '%s' by user '%s'.";
    public static final String RESERVATION_TIME_CONFLICT = "Conflict with reservation, existing reservation for specified start of '%s' or end of '%s'.";
    public static final String RESERVATION_NOT_BELONGS_TO_USER = "Reservation with id '%s' does not belong to user '%s'.";
    public static final String RESERVATION_LIMIT_EXCEEDED_IN_COURT_BY_USER = "Reservation limit exceeded for court '%s' by user '%s'.";
    public static final String RESERVATION_CANCELLATION_TIME_EXPIRED = "Cancellation time for reservation has expired.";
  
    public ReservationExeceptionMessages(){}
}
