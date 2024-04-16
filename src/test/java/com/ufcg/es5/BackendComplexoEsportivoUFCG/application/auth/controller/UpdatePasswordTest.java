package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.constants.AuthPathConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service.AuthService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthPasswordUpdateDto;
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

import static com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.constants.AuthAttributesConstants.VALID_PASSWORD;
import static com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.PropertyConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdatePasswordTest extends BasicTestController {

    private static AuthPasswordUpdateDto validPayload;
    private static Set<String> validRoles;

    @MockBean
    private AuthService authService;

    @BeforeEach
    void makeScenario() {
        validPayload = new AuthPasswordUpdateDto(VALID_PASSWORD, VALID_PASSWORD);
        validRoles = Set.of(ROLE_ADMIN);
    }

    @ParameterizedTest
    @DisplayName("Should return Success. Code: 204")
    @MethodSource(value = "returnNoContent")
    void returnNoContent(Set<String> roles) throws Exception {
        SecurityContextUtils.fakeAuthentication(roles);

        String payload = makeRequestPayload(validPayload);

        Mockito.doNothing().when(authService).updatePassword(validPayload);

        callEndpoint(payload).andExpect(status().isNoContent()).andReturn();
    }

    @ParameterizedTest
    @DisplayName("Should return BadRequest. Code: 400")
    @MethodSource(value = "returnBadRequest")
    void returnBadRequest(AuthPasswordUpdateDto passwordUpdateDto) throws Exception {
        SecurityContextUtils.fakeAuthentication(validRoles);
        String payload = makeRequestPayload(passwordUpdateDto);

        callEndpoint(payload).andExpect(status().isBadRequest()).andReturn();
    }

    @ParameterizedTest
    @DisplayName("Should return Forbidden. Code: 403")
    @MethodSource(value = "returnForbidden")
    void returnForbidden(Set<String> roles) throws Exception {
        SecurityContextUtils.fakeAuthentication(roles);

        String payload = makeRequestPayload(validPayload);

        callEndpoint(payload).andExpect(status().isForbidden()).andReturn();
    }

    private static Stream<Arguments> returnNoContent() {
        return Stream.of(
                Arguments.of(Set.of(ROLE_ADMIN)),
                Arguments.of(Set.of(ROLE_USER)),
                Arguments.of(Set.of(ROLE_ADMIN, ROLE_PENDING))
        );
    }

    private static Stream<Arguments> returnBadRequest() {
        return Stream.of(
                Arguments.of(new AuthPasswordUpdateDto("", VALID_PASSWORD)),
                Arguments.of(new AuthPasswordUpdateDto(" ", VALID_PASSWORD)),
                Arguments.of(new AuthPasswordUpdateDto(null, VALID_PASSWORD)),
                Arguments.of(new AuthPasswordUpdateDto(VALID_PASSWORD, "")),
                Arguments.of(new AuthPasswordUpdateDto(VALID_PASSWORD, " ")),
                Arguments.of(new AuthPasswordUpdateDto(VALID_PASSWORD, null))
        );
    }

    private static Stream<Arguments> returnForbidden() {
        return Stream.of(
                Arguments.of(Set.of(ROLE_PENDING))
        );
    }

    private String makeRequestPayload(AuthPasswordUpdateDto payload) throws JsonProcessingException {
        return objectMapper.writeValueAsString(payload);
    }

    private ResultActions callEndpoint(String payload) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .put(AuthPathConstants.UPDATE_PASSWORD_FULL_PATH)
                .content(payload)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );
    }
}
