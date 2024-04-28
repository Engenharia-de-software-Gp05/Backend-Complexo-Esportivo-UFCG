package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service.AuthService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.AuthPathConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthRegisterDataWithoutRolesDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthTokenDto;
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

import java.util.stream.Stream;

import static com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.constants.AuthAttributesConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegisterTest extends BasicTestController {


    @MockBean
    private AuthService authService;

    @ParameterizedTest
    @DisplayName("Should return Created. Code: 201")
    @MethodSource(value = "returnCreated")
    void returnCreated(AuthRegisterDataWithoutRolesDto registerDataWithoutRolesDto) throws Exception {
        String payload = makeRequestPayload(registerDataWithoutRolesDto);
        AuthTokenDto response = makeResponse();

        Mockito.when(authService.register(registerDataWithoutRolesDto))
                .thenReturn(response);

        callEndpoint(payload).andExpect(status().isCreated()).andReturn();
    }

    private static Stream<Arguments> returnCreated() {
        return Stream.of(
                Arguments.of(new AuthRegisterDataWithoutRolesDto(VALID_STUDENT_EMAIL, VALID_NAME, VALID_PHONE_NUMBER, VALID_STUDENT_ID, VALID_PASSWORD))
        );
    }

    @ParameterizedTest
    @DisplayName("Should return BadRequest ")
    @MethodSource(value = "returnBadRequest")
    void returnBadRequest(AuthRegisterDataWithoutRolesDto registerDataWithoutRolesDto) throws Exception {
        String payload = makeRequestPayload(registerDataWithoutRolesDto);

        callEndpoint(payload).andExpect(status().isBadRequest()).andReturn();
    }

    private static Stream<Arguments> returnBadRequest() {
        return Stream.of(
                Arguments.of(new AuthRegisterDataWithoutRolesDto("", VALID_NAME, VALID_PHONE_NUMBER, VALID_STUDENT_ID, VALID_PASSWORD)),
                Arguments.of(new AuthRegisterDataWithoutRolesDto(null, VALID_NAME, VALID_PHONE_NUMBER, VALID_STUDENT_ID, VALID_PASSWORD)),
                Arguments.of(new AuthRegisterDataWithoutRolesDto(VALID_STUDENT_EMAIL, "", VALID_PHONE_NUMBER, VALID_STUDENT_ID, VALID_PASSWORD)),
                Arguments.of(new AuthRegisterDataWithoutRolesDto(VALID_STUDENT_EMAIL, null, VALID_PHONE_NUMBER, VALID_STUDENT_ID, VALID_PASSWORD)),
                Arguments.of(new AuthRegisterDataWithoutRolesDto(VALID_STUDENT_EMAIL, VALID_NAME, "", VALID_STUDENT_ID, VALID_PASSWORD)),
                Arguments.of(new AuthRegisterDataWithoutRolesDto(VALID_STUDENT_EMAIL, VALID_NAME, null, VALID_STUDENT_ID, VALID_PASSWORD)),
                Arguments.of(new AuthRegisterDataWithoutRolesDto(VALID_STUDENT_EMAIL, VALID_NAME, VALID_PHONE_NUMBER, "12121121", VALID_PASSWORD)),
                Arguments.of(new AuthRegisterDataWithoutRolesDto(VALID_STUDENT_EMAIL, VALID_NAME, VALID_PHONE_NUMBER, "1212112112", VALID_PASSWORD)),
                Arguments.of(new AuthRegisterDataWithoutRolesDto(VALID_STUDENT_EMAIL, VALID_NAME, VALID_PHONE_NUMBER, "", VALID_PASSWORD)),
                Arguments.of(new AuthRegisterDataWithoutRolesDto(VALID_STUDENT_EMAIL, VALID_NAME, VALID_PHONE_NUMBER, null, VALID_PASSWORD)),
                Arguments.of(new AuthRegisterDataWithoutRolesDto(VALID_STUDENT_EMAIL, VALID_NAME, VALID_PHONE_NUMBER, VALID_STUDENT_ID, "")),
                Arguments.of(new AuthRegisterDataWithoutRolesDto(VALID_STUDENT_EMAIL, VALID_NAME, VALID_PHONE_NUMBER, VALID_STUDENT_ID, null))
        );
    }

    private String makeRequestPayload(AuthRegisterDataWithoutRolesDto payload) throws JsonProcessingException {
        return objectMapper.writeValueAsString(payload);
    }

    private AuthTokenDto makeResponse() {
        return new AuthTokenDto(FAKE_TOKEN);
    }

    private ResultActions callEndpoint(String payload) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post(AuthPathConstants.REGISTER_FULL_PATH)
                .content(payload)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );
    }
}
