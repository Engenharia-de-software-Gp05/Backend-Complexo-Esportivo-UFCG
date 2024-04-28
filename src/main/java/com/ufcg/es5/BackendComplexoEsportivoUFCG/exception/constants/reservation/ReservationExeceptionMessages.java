package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.reservation;

public class ReservationExeceptionMessages {

    public static final String RESERVATION_WITH_ID_NOT_FOUND = "Reservation with id '%s' not found.";
    public static final String RESERVATION_WITH_ID_NOT_BELONGS_TO_USER_WITH_ID = "Reservation with id '%s' does not belong to user '%s'.";
    public static final String RESERVATION_CANCELLATION_TIME_EXPIRED = "Cancellation time for reservation has expired.";
    public static final String RESERVATION_LIMIT_EXCEEDED_FOR_INTERVAL_TIME_BETWEEN_RESERVATIONS = "Reservation limit exceeded for interval time between reservations.";
    public static final String RESERVATION_TIME_CONFLICT = "Conflict with reservation, existing reservation for specified start of '%s' or end of '%s'.";
    public static final String USER_WITH_ID_ALREADY_HAS_A_RESERVATION_FOR_START_DATE_TIME = "User with id '%s' already has a reservation with start date time at '%s'";
    public static final String UNAVAILABILITY_FOUND_FOR_THE_GIVEN_TIME = "There is an unavailability for the given time";

    public ReservationExeceptionMessages() {
    }
}
