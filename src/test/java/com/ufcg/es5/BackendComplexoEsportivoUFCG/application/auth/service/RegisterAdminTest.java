package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.service.BasicTestService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.service.MailService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthRegisterDataWithRolesDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserNameEmailDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceConflictException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

class RegisterAdminTest extends BasicTestService {
    private static final String USER_EMAIL = "username@gmail.com";
    public static final String NAME = "Heisenberg";
    public static final String PHONE_NUMBER1 = "83 966666666";
    public static final String PHONE_NUMBER2 = "83 977777777";

    @Autowired
    private SaceUserService userService;

    @MockBean
    private MailService mailService;

    @Autowired
    private AuthService authService;
    private AuthRegisterDataWithRolesDto authRegisterDataWithRolesDto;

    @BeforeEach
    @Transactional
    void prepareTestScenario() {
        authRegisterDataWithRolesDto = new AuthRegisterDataWithRolesDto(
                USER_EMAIL,
                NAME,
                PHONE_NUMBER1,
                Set.of(SaceUserRoleEnum.ROLE_USER)
        );

        Mockito.doNothing().when(mailService).sendSignUpTemporaryPasswordEmail(Mockito.eq(NAME), Mockito.anyString(), Mockito.eq(USER_EMAIL));
    }

    @Test
    @Transactional
    @DisplayName("Successful registration with email should return authentication token")
    void shouldReturnTokenWhenRegisterSuccessfully() {
        SaceUserNameEmailDto expected = new SaceUserNameEmailDto(USER_EMAIL, NAME);
        SaceUserNameEmailDto result = authService.registerByAdmin(authRegisterDataWithRolesDto);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @Transactional
    @DisplayName("Should throw exception when trying to register with already registered email")
    void shouldThrowExceptionWhenTryRegisterWithEmailAlreadyRegistered() {
        authService.registerByAdmin(authRegisterDataWithRolesDto);

        AuthRegisterDataWithRolesDto authRegisterDataWithSameEmailDto =
                new AuthRegisterDataWithRolesDto(
                        USER_EMAIL,
                        NAME,
                        PHONE_NUMBER2,
                        Set.of(SaceUserRoleEnum.ROLE_ADMIN)
                );

        Assertions.assertThrows(
                SaceConflictException.class,
                () -> authService.registerByAdmin(authRegisterDataWithSameEmailDto)
        );
    }
}
