package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.service.BasicTestService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.token.TokenService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthRegisterDataWithoutRolesDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthTokenDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
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

class RegisterTest extends BasicTestService {

    private static final String USER_EMAIL1 = "username@gmail.com";
    private static final String USER_EMAIL2 = "username2@gmail.com";
    private static final String STUDENT_ID1 = "121212121";
    private static final String STUDENT_ID2 = "131313131";
    public static final String NAME = "Heisenberg";
    public static final String PHONE_NUMBER1 = "83 966666666";
    public static final String PHONE_NUMBER2 = "83 977777777";
    public static final String PASSWORD = "12345678";
    public static final String FAKE_TOKEN = "jhoaodlvhwravhwhoeab";

    @Autowired
    private SaceUserService userService;

    @MockBean
    private TokenService tokenService;

    @Autowired
    private AuthService authService;
    private AuthRegisterDataWithoutRolesDto authRegisterDataWithoutRolesDto;

    @BeforeEach
    @Transactional
    void makeTestScenario() {
        authRegisterDataWithoutRolesDto = new AuthRegisterDataWithoutRolesDto(
                USER_EMAIL1,
                NAME,
                PHONE_NUMBER1,
                STUDENT_ID1,
                PASSWORD
        );
        Mockito.when(tokenService.generateToken(Mockito.any(SaceUser.class), Mockito.anyLong())).thenReturn(FAKE_TOKEN);
    }

    @Test
    @Transactional
    @DisplayName("Successful registration with email should return authentication token")
    void shouldReturnTokenWhenRegisterSuccessfully() {
        AuthTokenDto authTokenDto = authService.register(authRegisterDataWithoutRolesDto);

        Assertions.assertFalse(authTokenDto.token().isEmpty());

        Set<SaceUserRoleEnum> expected = Set.of(SaceUserRoleEnum.ROLE_PENDING);
        Set<SaceUserRoleEnum> result = userService.findByEmail(USER_EMAIL1).get().getRoleEnums();

        org.assertj.core.api.Assertions.assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    @Transactional
    @DisplayName("Should throw exception when trying to register with already registered email")
    void shouldThrowExceptionWhenTryRegisterWithEmailAlreadyRegistered() {
        authService.register(authRegisterDataWithoutRolesDto);

        AuthRegisterDataWithoutRolesDto authRegisterDataWithSameEmailDto =
                new AuthRegisterDataWithoutRolesDto(
                        USER_EMAIL1,
                        NAME,
                        PHONE_NUMBER2,
                        STUDENT_ID2,
                        PASSWORD
                );

        Assertions.assertThrows(
                SaceConflictException.class,
                () -> authService.register(authRegisterDataWithSameEmailDto)
        );
    }

    @Test
    @Transactional
    @DisplayName("Should throw exception when trying to register with already registered student ID")
    void shouldThrowExceptionWhenTryRegisterWithStudentIdAlreadyRegistered() {
        authService.register(authRegisterDataWithoutRolesDto);

        AuthRegisterDataWithoutRolesDto authRegisterDataWithSameStudentIdDto =
                new AuthRegisterDataWithoutRolesDto(
                        USER_EMAIL2,
                        NAME,
                        PHONE_NUMBER2,
                        STUDENT_ID1,
                        PASSWORD
                );

        Assertions.assertThrows(
                SaceConflictException.class,
                () -> authService.register(authRegisterDataWithSameStudentIdDto)
        );
    }
}
