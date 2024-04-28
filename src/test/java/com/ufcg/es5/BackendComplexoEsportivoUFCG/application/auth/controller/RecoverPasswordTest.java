package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service.AuthService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.AuthPathConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.PropertyTestConstants;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.constants.AuthAttributesConstants.VALID_STUDENT_EMAIL;
import static com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.constants.AuthAttributesConstants.VALID_STUDENT_ID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecoverPasswordTest extends BasicTestController {

    @MockBean
    private AuthService authService;

    @ParameterizedTest
    @DisplayName("Should return Success. Code: 200")
    @MethodSource(value = "returnSuccess")
    void returnSuccess(String username) throws Exception {
        String payload = makeRequestPayload(username);

        Mockito.doNothing().when(authService).recoverPassword(username);

        callEndpoint(payload).andExpect(status().isOk()).andReturn();
    }

    @ParameterizedTest
    @DisplayName("Should return BadRequest ")
    @MethodSource(value = "returnBadRequest")
    void returnBadRequest(String username) throws Exception {

        callEndpoint(username).andExpect(status().isBadRequest()).andReturn();
    }

    private static Stream<Arguments> returnSuccess() {
        return Stream.of(
                Arguments.of(VALID_STUDENT_EMAIL),
                Arguments.of(VALID_STUDENT_ID)
        );
    }

    private static Stream<Arguments> returnBadRequest() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("      "),
                null
        );
    }

    private String makeRequestPayload(String username) throws JsonProcessingException {
        Map<String, Object> payload = new HashMap<>();

        payload.put(PropertyTestConstants.USERNAME, username);

        return objectMapper.writeValueAsString(payload);
    }

    private ResultActions callEndpoint(String username) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post(AuthPathConstants.RECOVER_PASSWORD_FULL_PATH)
                .param(PropertyTestConstants.USERNAME, username)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );
    }

}
