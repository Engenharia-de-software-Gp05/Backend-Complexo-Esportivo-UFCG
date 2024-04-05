package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.reservation;

public class ReservationExeceptionMessages extends IllegalArgumentException{

    public static final String RESERVATION_WITH_ID_NOT_FOUND = "Reservation with id '%s' not found.";
    public static final String RESERVATION_NOT_BELONGS_TO_USER = "Reservation with id '%s' does not belong to user '%s'.";
    public static final String RESERVATION_LIMIT_EXCEEDED_IN_COURT_BY_USER = "Reservation limit exceeded for court '%s' by user '%s'.";
    public static final String RESERVATION_wITHOUT_WAIT_MINIMUM_TIME_BY_USER_IN_COURT = "Reservation without wait minimum time by user '%s' in court '%s'.";
    public static final String ALREAD_EXISTS_A_RESERVATION_AT_DATE_TIME_BY_USER_IN_COURT = "Already exists a reservation at date time '%s' by user '%s' in court '%s'.";

    public static final String RESERVATION_WITH_ID_NOT_FOUND = "Reservation with id '%s' not found.";
    public static final String RESERVATION_NOT_BELONGS_TO_USER = "Reservation with id '%s' does not belong to user '%s'.";
    public static final String RESERVATION_LIMIT_EXCEEDED_IN_COURT_BY_USER = "Reservation limit exceeded for court '%s' by user '%s'.";
    public static final String RESERVATION_PERMISSION_DENIED = "User has no permission the reservation.";
  
    public ReservationExeceptionMessages(){}
}
