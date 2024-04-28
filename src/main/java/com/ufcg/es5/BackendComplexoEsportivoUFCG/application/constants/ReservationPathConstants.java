package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants;

public class ReservationPathConstants {

    public static final String PREFIX = "/reservation";
    public static final String FIND_BY_COURT_ID_AND_USER_ID_PATH = "/by/court-id/user-id";
    public static final String FIND_BY_COURT_ID_USER_ID_FULL_PATH = PREFIX + FIND_BY_COURT_ID_AND_USER_ID_PATH;
    public static final String FIND_BY_COURT_ID_AND_DATE_RANGE_PATH = "/by/courtId/dateRange";
    public static final String FIND_BY_COURT_ID_AND_DATE_RANGE_FULL_PATH = PREFIX + FIND_BY_COURT_ID_AND_DATE_RANGE_PATH;
    public static final String CREATE_PATH = "/create";
    public static final String CREATE_FULL_PATH = PREFIX + CREATE_PATH;
    public static final String DELETE_BY_ID_PATH = "/delete/by/id";
    public static final String DELETE_BY_ID_FULL_PATH = PREFIX + DELETE_BY_ID_PATH;
    public static final String DELETE_BY_ID_AND_MOTIVE_PATH = "/delete/by/id/motive";
    public static final String DELETE_BY_ID_AND_MOTIVE_FULL_PATH = PREFIX + DELETE_BY_ID_AND_MOTIVE_PATH;
    public static final String FIND_DETAILED_BY_AUTHENTICATED_USER_PATH = "/detailed/by/authenticatedUser";
    public static final String FIND_DETAILED_BY_AUTHENTICATED_USER_FULL_PATH = PREFIX + FIND_DETAILED_BY_AUTHENTICATED_USER_PATH;
    public static final String FIND_ALL_DETAILED_PATH = "/all/detailed";
    public static final String FIND_ALL_DETAILED_FULL_PATH = PREFIX + FIND_ALL_DETAILED_PATH;

    private ReservationPathConstants() {
    }
}
