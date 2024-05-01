package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants;

public class CourtPathConstants {

    public static final String PREFIX = "/court";
    public static final String CREATE_PATH = "/create";
    public static final String CREATE_FULL_PATH = PREFIX + CREATE_PATH;
    public static final String FIND_ALL_PATH = "/all";
    public static final String FIND_ALL_FULL_PATH = PREFIX + FIND_ALL_PATH;
    public static final String UPLOAD_IMAGE_PATH = "/upload/image";
    public static final String UPLOAD_IMAGE_FULL_PATH = PREFIX + UPLOAD_IMAGE_PATH;
    public static final String DELETE_BY_ID_PATH = "/by/id";
    public static final String DELETE_BY_ID_FULL_PATH = PREFIX + DELETE_BY_ID_PATH;
    public static final String UPDATE_BY_ID_PATH = "/by/id";
    public static final String UPDATE_BY_ID_FULL_PATH = PREFIX + UPDATE_BY_ID_PATH;
    public static final String FIND_BY_ID_PATH = "/by/id";
    public static final String FIND_BY_ID_FULL_PATH = PREFIX + FIND_BY_ID_PATH;

    private CourtPathConstants() {
    }
}
