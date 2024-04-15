package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.sace_user;

public class SaceUserExceptionMessages {

    public static final String USER_WITH_ID_NOT_FOUND = "User with id '%s' not found.";
    public static final String USER_WITH_EMAIL_NOT_FOUND = "User with email '%s' not found.";
    public static final String USER_WITH_USERNAME_NOT_FOUND = "User with username '%s' not found.";
    public static final String USER_WITH_EMAIL_ALREADY_EXISTS = "User with email '%s' already exists.";
    public static final String USER_WITH_STUDENT_ID_ALREADY_EXISTS = "User with student id '%s' already exists.";
    public static final String USER_WITH_ID_ALREADY_HAS_AN_ACTIVE_CODE = "User with id '%s' already has an active code.";
    public static final String CONFIRMATION_CODE_IS_NOT_RELATED_TO_USER_WITH_ID = "Confirmation code '%s' is not related to user with id '%s'";
    public static final String CONFIRMATION_CODE_EXPIRED = "Confirmation code expired";
    public static final String WRONG_PASSWORD_FOR_USER_WITH_ID = "Wrong password for user with id '%s'.";

    public SaceUserExceptionMessages() {
    }
}
