package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.service.BasicTestService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.service.MailService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.token.TokenService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

class RecoverPasswordTest extends BasicTestService {

    private static final String USER_EMAIL = "username@gmail.com";
    private static final String USER_PASSWORD = "12345678";
    private static final String STUDENT_ID = "121212121";
    private static final String FAKE_TOKEN = "jhoaodlvhwravhwhoeab";
    private static final String NAME = "Heisenberg";
    private static final String PHONE_NUMBER = "83 966666666";

    @Autowired
    private SaceUserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private MailService mailService;

    @BeforeEach
    @Transactional
    void prepareTestScenario() {

        String encodedPassword = passwordEncoder.encode(USER_PASSWORD);
        SaceUser user = new SaceUser(
                USER_EMAIL,
                NAME,
                PHONE_NUMBER,
                STUDENT_ID,
                encodedPassword,
                Set.of(SaceUserRoleEnum.ROLE_USER)
        );

        userService.save(user);
        Mockito.when(tokenService.generateToken(Mockito.any(SaceUser.class), Mockito.anyLong())).thenReturn(FAKE_TOKEN);
        Mockito.doNothing().when(mailService).sendRecoverPasswordLinkEmail(Mockito.eq(NAME), Mockito.anyString(), Mockito.eq(USER_EMAIL));
    }

    @Test
    @Transactional
    @DisplayName("Should successfully recover password when email or student ID exists")
    void shouldSucceedWhenUsernameExists() {
        Assertions.assertDoesNotThrow(
                () -> authService.recoverPassword(USER_EMAIL)
        );

        Assertions.assertDoesNotThrow(
                () -> authService.recoverPassword(STUDENT_ID)
        );
    }

    @Test
    @Transactional
    @DisplayName("Should throw exception when username does not exist")
    void shouldThrowExceptionWhenUsernameNotExists() {
        Assertions.assertThrows(
                SaceResourceNotFoundException.class,
                () -> authService.recoverPassword("INVALID")
        );
    }
}
