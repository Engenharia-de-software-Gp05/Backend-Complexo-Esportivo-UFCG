package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.service.BasicTestService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.register_confirmation_code.service.SignUpConfirmationCodeService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceInvalidArgumentException;
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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

class ConfirmRegisterTest extends BasicTestService {

    private static final String USER_EMAIL = "username@gmail.com";
    private static final String USER_PASSWORD = "12345678";
    private static final String STUDENT_ID = "121212121";

    @Autowired
    private SaceUserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SignUpConfirmationCodeService confirmationCodeService;

    @MockBean
    private AuthenticatedUser authenticatedUser;

    private static SaceUser user;

    @BeforeEach
    @Transactional
    void makeTestScenario() {

        String encodedPassword = passwordEncoder.encode(USER_PASSWORD);
        HashSet<SaceUserRoleEnum> saceUserRoleEnums = new HashSet<>();
        saceUserRoleEnums.add(SaceUserRoleEnum.ROLE_PENDING);
        user = new SaceUser(
                USER_EMAIL,
                "Heisenberg",
                "83 966666666",
                STUDENT_ID,
                encodedPassword,
                saceUserRoleEnums
        );

        userService.save(user);

        Mockito.when(authenticatedUser.getAuthenticatedUserId()).thenReturn(user.getId());
    }

    @Test
    @Transactional
    @DisplayName("Successful confirmation of registration with correct confirmation code")
    void shouldConfirmRegisterSuccessfully() {
        Set<SaceUserRoleEnum> expected = Set.of(SaceUserRoleEnum.ROLE_PENDING);
        Set<SaceUserRoleEnum> result = userService.findByEmail(user.getEmail()).get().getRoleEnums();

        org.assertj.core.api.Assertions.assertThat(result).containsExactlyElementsOf(expected);

        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5L);

        String confirmationCode = confirmationCodeService.save(user.getId(), expirationTime).confirmationCode();

        authService.confirmEmailRegistered(confirmationCode);

        Set<SaceUserRoleEnum> expectedAfterConfirm = Set.of(SaceUserRoleEnum.ROLE_USER);
        Set<SaceUserRoleEnum> actualAfterConfirm = userService.findByEmail(user.getEmail()).get().getRoleEnums();

        org.assertj.core.api.Assertions.assertThat(actualAfterConfirm).containsExactlyElementsOf(expectedAfterConfirm);
    }

    @Test
    @Transactional
    @DisplayName("Should throw exception when confirmation code is not found")
    void shouldThrowExceptionWhenConfirmationCodeNotFound() {
        Assertions.assertThrows(
                SaceResourceNotFoundException.class,
                () -> authService.confirmEmailRegistered("123456")
        );
    }

    @Test
    @Transactional
    @DisplayName("Should throw exception when confirmation code is expired")
    void shouldThrowExceptionWhenCodeExpired() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(5L);

        String confirmationCode = confirmationCodeService.save(user.getId(), expirationTime).confirmationCode();

        Assertions.assertThrows(
                SaceInvalidArgumentException.class,
                () -> authService.confirmEmailRegistered(confirmationCode)
        );
    }
}
