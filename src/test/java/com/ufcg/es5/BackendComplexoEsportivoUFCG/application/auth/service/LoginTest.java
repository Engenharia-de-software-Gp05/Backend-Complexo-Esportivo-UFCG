package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.service.BasicTestService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthTokenDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthUsernamePasswordDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

class LoginTest extends BasicTestService {

    private static final String USER_EMAIL = "username@gmail.com";
    private static final String USER_PASSWORD = "12345678";
    private static AuthUsernamePasswordDto rightCredentials;
    private static final String STUDENT_ID = "121212121";

    @Autowired
    private SaceUserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    @Transactional
    void makeTestScenario() {

        String encodedPassword = passwordEncoder.encode(USER_PASSWORD);
        SaceUser user = new SaceUser(
                USER_EMAIL,
                "Heisenberg",
                "83 966666666",
                STUDENT_ID,
                encodedPassword,
                Set.of(SaceUserRoleEnum.ROLE_USER)
        );

        userService.save(user);
    }

    @Test
    @Transactional
    @DisplayName("Successful login with email should return authentication token")
    void shouldReturnTokenWhenLoginSuccessfullyWithEmail() {
        rightCredentials = new AuthUsernamePasswordDto(USER_EMAIL, USER_PASSWORD);
        AuthTokenDto authTokenDto = authService.login(rightCredentials);

        Assertions.assertFalse(authTokenDto.token().isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Successful login with student ID should return authentication token")
    void shouldReturnTokenWhenLoginSuccessfullyWithStudentId() {
        rightCredentials = new AuthUsernamePasswordDto(STUDENT_ID, USER_PASSWORD);
        AuthTokenDto authTokenDto = authService.login(rightCredentials);

        Assertions.assertFalse(authTokenDto.token().isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Should throw exception when there's no user with the provided username")
    void shouldThrowExceptionWhenUserNotFound() {
        AuthUsernamePasswordDto invalidCredentials = new AuthUsernamePasswordDto("INVALID", USER_PASSWORD);

        Assertions.assertThrows(SaceResourceNotFoundException.class,
                () -> authService.login(invalidCredentials));
    }

    @Test
    @Transactional
    @DisplayName("Should throw exception when the provided password does not match the user's password")
    void shouldThrowExceptionWhenPasswordNotMatches() {
        AuthUsernamePasswordDto invalidCredentials = new AuthUsernamePasswordDto(USER_EMAIL, "123456789");

        Assertions.assertThrows(BadCredentialsException.class,
                () -> authService.login(invalidCredentials));
    }
    
}
