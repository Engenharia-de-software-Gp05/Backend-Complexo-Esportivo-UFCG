package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants;

public class AuthPathConstants {
    public static final String PREFIX = "/auth";
    public static final String REGISTER_PATH = "/register";
    public static final String REGISTER_FULL_PATH = PREFIX + REGISTER_PATH;
    public static final String REGISTER_ADMIN_PATH = "/admin/register";
    public static final String REGISTER_ADMIN_FULL_PATH = PREFIX + REGISTER_ADMIN_PATH;
    public static final String LOGIN_PATH = "/login";
    public static final String LOGIN_FULL_PATH = PREFIX + LOGIN_PATH;
    public static final String RECOVER_PASSWORD_PATH = "/recover-password";
    public static final String RECOVER_PASSWORD_FULL_PATH = PREFIX + RECOVER_PASSWORD_PATH;
    public static final String UPDATE_PASSWORD_PATH = "/update/password";
    public static final String UPDATE_PASSWORD_FULL_PATH = PREFIX + UPDATE_PASSWORD_PATH;
    public static final String CONFIRM_REGISTER_PATH = "/confirm/register";
    public static final String CONFIRM_REGISTER_FULL_PATH = PREFIX + CONFIRM_REGISTER_PATH;

    private AuthPathConstants() {
    }
}
