package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service.AuthService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.PropertyConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthRegisterDataWithoutRolesDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthTokenDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthUsernamePasswordDto;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegisterTest extends BasicTestController {
    private static final String PATH = "/auth/register";
    public static final String FAKE_TOKEN = "vabiabvblsivbabvilqbvilvbudvdhkbabieu51694";

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @DisplayName("Should return Created. Code: 201")
    @MethodSource(value = "returnCreated")
    void returnCreated(AuthRegisterDataWithoutRolesDto registerDataWithoutRolesDto) throws Exception {
        String payload = makeRequestPayload(registerDataWithoutRolesDto);
        System.out.println(payload);
        AuthTokenDto response = makeResponse();

        Mockito.when(authService.register(registerDataWithoutRolesDto))
                .thenReturn(response);

        callEndpoint(payload).andExpect(status().isCreated()).andReturn();
    }

    private static Stream<Arguments> returnCreated() {
        return Stream.of(
                Arguments.of(new AuthRegisterDataWithoutRolesDto("email@gmail.com","name", "83966666666", "1212121212", "password"))
                );
    }

    private String makeRequestPayload(AuthRegisterDataWithoutRolesDto payload) throws JsonProcessingException {
        return objectMapper.writeValueAsString(payload);
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
                Arguments.of(new AuthRegisterDataWithoutRolesDto("","name", "83966666666", "1212121212", "password")),
                Arguments.of(new AuthRegisterDataWithoutRolesDto(null,"name", "83966666666", "1212121212", "password")),
                Arguments.of(new AuthRegisterDataWithoutRolesDto("email","", "83966666666", "1212121212", "password")),
                Arguments.of(new AuthRegisterDataWithoutRolesDto("email",null, "83966666666", "1212121212", "password")),
                Arguments.of(new AuthRegisterDataWithoutRolesDto("email","name", "", "1212121212", "password")),
                Arguments.of(new AuthRegisterDataWithoutRolesDto("email","name", null, "1212121212", "password")),
                Arguments.of(new AuthRegisterDataWithoutRolesDto("email","name", "83966666666", "", "password")),
                Arguments.of(new AuthRegisterDataWithoutRolesDto("email","name", "83966666666", null, "password")),
                Arguments.of(new AuthRegisterDataWithoutRolesDto("email","name", "83966666666", "1212121212", "")),
                Arguments.of(new AuthRegisterDataWithoutRolesDto("email","name", "83966666666", "1212121212", null))
        );
    }
    private ResultActions callEndpoint(String payload) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post(PATH)
                .content(payload)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );
    }

    private AuthTokenDto makeResponse() {
        return new AuthTokenDto(FAKE_TOKEN);
    }
}
