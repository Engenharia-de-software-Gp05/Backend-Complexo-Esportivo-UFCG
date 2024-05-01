package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants;

public class SaceUserPathConstants {

    public static final String PREFIX = "/user";
    public static final String FIND_BY_EMAIL_PATH = "/by/email";
    public static final String FIND_BY_EMAIL_FULL_PATH = PREFIX + FIND_BY_EMAIL_PATH;
    public static final String FIND_ALL_PATH = "/all";
    public static final String FIND_ALL_FULL_PATH = PREFIX + FIND_ALL_PATH;
    public static final String UPLOAD_PROFILE_PICTURE_PATH = "/upload/profile/picture";
    public static final String UPLOAD_PROFILE_PICTURE_FULL_PATH = PREFIX + UPLOAD_PROFILE_PICTURE_PATH;
    public static final String DELETE_BY_ID_PATH = "/by/id";
    public static final String DELETE_BY_ID_FULL_PATH = PREFIX + DELETE_BY_ID_PATH;

    private SaceUserPathConstants() {
    }
}
