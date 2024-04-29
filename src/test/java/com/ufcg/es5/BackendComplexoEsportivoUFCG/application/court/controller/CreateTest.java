package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.PropertyTestConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security.SecurityContextUtils;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CreateTest extends BasicTestController {

    private static final String PATH = "/court/save";

    @MockBean
    private CourtService courtService;

    @BeforeEach
    void setUp() {
    }

    @ParameterizedTest
    @DisplayName("Should return Success. Code: 201")
    @MethodSource(value = "returnCreate")
    void returnNoCreate(List<String> roles) throws Exception {
        CourtSaveDto data = new CourtSaveDto(
                "Novo nome",
                90L,
                10L
        );

        SecurityContextUtils.fakeAuthentication(roles);
        callEndpoint(data).andExpect(status().isCreated()).andReturn();
    }

    private static Stream<Arguments> returnCreate() {
        return Stream.of(
                Arguments.of(List.of(PropertyTestConstants.ROLE_ADMIN))
        );
    }

    @Test
    @DisplayName("should return badrequest for invalid parameters passed to dto")
    void returnBadRequestByDto() throws Exception {
        CourtSaveDto data = new CourtSaveDto(
                null,
                90L,
                10L
        );

        SecurityContextUtils.fakeAuthentication(List.of(PropertyTestConstants.ROLE_ADMIN));

        ResultActions resultActions = mockMvc.perform(post(PATH)
                .content(objectMapper.writeValueAsString(data))
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @DisplayName("Should return Forbidden. Code: 403.")
    void returnForbidden() throws Exception {
        CourtSaveDto data = new CourtSaveDto(
                "Nome quadra",
                90L,
                10L
        );

        SecurityContextUtils.fakeAuthentication(List.of(PropertyTestConstants.ROLE_USER));
        callEndpoint(data).andExpect(status().isForbidden()).andReturn();
    }

    private ResultActions callEndpoint(CourtSaveDto data) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post(PATH)
                .content(objectMapper.writeValueAsString(data))
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );
    }
}
