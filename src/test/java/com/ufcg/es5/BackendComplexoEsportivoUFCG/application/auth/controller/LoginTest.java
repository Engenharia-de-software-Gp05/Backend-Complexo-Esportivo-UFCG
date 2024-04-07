package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service.AuthService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.PropertyConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthTokenDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthUsernamePasswordDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security.SecurityContextUtils;
import org.apache.http.HttpHeaders;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

class LoginTest extends BasicTestController {
    private static final String PATH = "/auth/login";
    public static final String FAKE_TOKEN = "vabiabvblsivbabvilqbvilvbudvdhkbabieu51694";

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @DisplayName("Should return Success. Code: 200")
    @MethodSource(value = "returnSuccess")
    void returnSuccess(String username, String password) throws Exception {
        String payload = makeRequestPayload(username, password);
        AuthUsernamePasswordDto servicePayload = new AuthUsernamePasswordDto(username, password);
        AuthTokenDto response = makeResponse();

        Mockito.when(authService.login(servicePayload))
                .thenReturn(response);

        callEndpoint(payload).andExpect(status().isOk()).andReturn();
    }

    private static Stream<Arguments> returnSuccess() {
        return Stream.of(
                Arguments.of("username", "password"),
                Arguments.of("12121212", "password")
        );
    }

    private String makeRequestPayload(String username, String password) throws JsonProcessingException {
        Map<String, Object> payload = new HashMap<>();

        payload.put(PropertyConstants.USERNAME, username);
        payload.put(PropertyConstants.PASSWORD, password);

        return objectMapper.writeValueAsString(payload);
    }

    @ParameterizedTest
    @DisplayName("Should return BadRequest ")
    @MethodSource(value = "returnBadRequest")
    void returnBadRequest(String username, String password) throws Exception {
        String payload = makeRequestPayload(username, password);

        callEndpoint(payload).andExpect(status().isBadRequest()).andReturn();
    }
    private static Stream<Arguments> returnBadRequest() {
        return Stream.of(
                Arguments.of("", "password"),
                Arguments.of("12121212", ""),
                Arguments.of(null, "password"),
                Arguments.of("12121212", null)
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
