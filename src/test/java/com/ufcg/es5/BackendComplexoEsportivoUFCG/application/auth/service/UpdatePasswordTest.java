package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.service.BasicTestService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthPasswordUpdateDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceForbiddenException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

class UpdatePasswordTest extends BasicTestService {

    private static final String USER_EMAIL = "username@gmail.com";
    private static final String STUDENT_ID = "121212121";
    public static final String NAME = "Heisenberg";
    public static final String PHONE_NUMBER = "83 966666666";
    public static final String PASSWORD = "12345678";

    @Autowired
    private SaceUserService userService;

    @Autowired
    private AuthService authService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticatedUser authenticatedUser;

    private static SaceUser user;

    @BeforeEach
    @Transactional
    void makeTestScenario() {
        Set<SaceUserRoleEnum> saceUserRoleEnums = new HashSet<>();
        saceUserRoleEnums.add(SaceUserRoleEnum.ROLE_USER);

        user = new SaceUser(
                USER_EMAIL,
                NAME,
                PHONE_NUMBER,
                STUDENT_ID,
                PASSWORD,
                saceUserRoleEnums
        );

        userService.save(user);

        Mockito.when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        Mockito.when(authenticatedUser.getAuthenticatedUserId()).thenReturn(user.getId());
    }

    @Test
    @Transactional
    @DisplayName("Update password successfully when providing correct current password")
    void shouldUpdatePasswordSuccessfullyWithCorrectCurrentPassword() {

        String newPassword = "newPassword";
        Mockito.when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);

        AuthPasswordUpdateDto passwordUpdateDto = new AuthPasswordUpdateDto(PASSWORD, newPassword);

        authService.updatePassword(passwordUpdateDto);

        Assertions.assertEquals(newPassword, user.getPassword());
    }

    @Test
    @Transactional
    @DisplayName("Throw exception when providing incorrect current password")
    void shouldThrowExceptionWhenProvidingIncorrectCurrentPassword() {

        String newPassword = "newPassword";
        Mockito.when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);

        AuthPasswordUpdateDto passwordUpdateDto = new AuthPasswordUpdateDto("INVALID", newPassword);

        Assertions.assertThrows(
                SaceForbiddenException.class,
                () -> authService.updatePassword(passwordUpdateDto)
        );
    }
}
