package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.constants;

public class ReservationPathConstants {
    public static final String PREFIX = "/reservation";
    public static final String BY_ID_PATH = "/by/id";
    public static final String BY_COURT_PATH = "/by/court";
    public static final String BY_COURT_AND_DATE_PATH = "/by/court";
    public static final String CREATE_PATH = "/create";
    public static final String DELETE_PATH = "/delete";
    public static final String UPDATE_PATH = "/update";
    public static final String DELETE_BY_ID_PATH = "/delete/by/id";
    public static final String ADMIN_PATH = "/admin";
    public static final String ADMIN_DELETE_BY_ID_PATH = "/admin/delete/by/id";
    public static final String GET_BY_ID_FULL_PATH = PREFIX + BY_ID_PATH;
    public static final String GET_BY_COURT_FULL_PATH = PREFIX + BY_COURT_PATH;
    public static final String DELETE_BY_ID_FULL_PATH = PREFIX + DELETE_BY_ID_PATH;
    public static final String CREATE_FULL_PATH = PREFIX + CREATE_PATH;

    private ReservationPathConstants() {
    }
}
