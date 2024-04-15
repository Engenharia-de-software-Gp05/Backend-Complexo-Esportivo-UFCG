package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.constants.AuthPathConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service.AuthService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.global.PropertyConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security.SecurityContextUtils;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;
import java.util.stream.Stream;

import static com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.PropertyConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConfirmRegisterTest extends BasicTestController {

    private static final String validConfirmationCode = "123456";
    private static Set<String> validRoles;

    @MockBean
    private AuthService authService;

    @BeforeEach
    void makeScenario() {
        validRoles = Set.of(ROLE_PENDING);
    }

    @ParameterizedTest
    @DisplayName("Should return Success. Code: 200")
    @MethodSource(value = "returnSuccess")
    void returnSuccess(Set<String> roles) throws Exception {
        SecurityContextUtils.fakeAuthentication(roles);

        Mockito.doNothing().when(authService).confirmEmailRegistered(validConfirmationCode);

        callEndpoint(validConfirmationCode).andExpect(status().isOk()).andReturn();
    }

    @ParameterizedTest
    @DisplayName("Should return BadRequest ")
    @MethodSource(value = "returnBadRequest")
    void returnBadRequest(String confirmationCode) throws Exception {
        SecurityContextUtils.fakeAuthentication(validRoles);

        callEndpoint(confirmationCode).andExpect(status().isBadRequest()).andReturn();
    }

    @ParameterizedTest
    @DisplayName("Should return Forbidden. Code: 403")
    @MethodSource(value = "returnForbidden")
    void returnForbidden(Set<String> roles) throws Exception {
        SecurityContextUtils.fakeAuthentication(roles);

        callEndpoint(validConfirmationCode).andExpect(status().isForbidden()).andReturn();
    }

    private static Stream<Arguments> returnSuccess() {
        return Stream.of(
                Arguments.of(Set.of(ROLE_PENDING))
        );
    }

    private static Stream<Arguments> returnBadRequest() {
        return Stream.of(
                null,
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("12345"),
                Arguments.of("1234567")
        );
    }

    private static Stream<Arguments> returnForbidden() {
        return Stream.of(
                Arguments.of(Set.of(ROLE_ADMIN)),
                Arguments.of(Set.of(ROLE_USER))
        );
    }

    private ResultActions callEndpoint(String confirmationCode) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post(AuthPathConstants.CONFIRM_REGISTER_FULL_PATH)
                .param(PropertyConstants.CONFIRMATION_CODE, confirmationCode)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );
    }
}
