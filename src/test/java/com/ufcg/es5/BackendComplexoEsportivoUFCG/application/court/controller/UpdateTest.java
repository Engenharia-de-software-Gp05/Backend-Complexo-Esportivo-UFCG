package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.PropertyTestConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtUpdateDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UpdateTest extends BasicTestController {

    private static final String PATH = "/court/update/by/id";


    @MockBean
    private CourtService courtService;

    @BeforeEach
    void setUp() {
    }

    @ParameterizedTest
    @DisplayName("Should return Success. Code: 200")
    @MethodSource(value = "returnNoContent")
    void returnNoCreate(List<String> roles) throws Exception {

        CourtUpdateDto data = new CourtUpdateDto(
                "Novo nome",
                CourtAvailabilityStatusEnum.UNAVAILABLE
        );

        SecurityContextUtils.fakeAuthentication(roles);
        callEndpoint(data, 1L).andExpect(status().isOk()).andReturn();
    }

    private static Stream<Arguments> returnNoContent() {
        return Stream.of(
                Arguments.of(List.of(PropertyTestConstants.ROLE_ADMIN))
        );
    }

    @Test
    @DisplayName("should return badrequest for invalid parameters passed to dto")
    void returnBadRequestByDto1() throws Exception {

        CourtUpdateDto data = new CourtUpdateDto(
                null,
                CourtAvailabilityStatusEnum.AVAILABLE
        );

        SecurityContextUtils.fakeAuthentication(List.of(PropertyTestConstants.ROLE_ADMIN));

        ResultActions resultActions = mockMvc.perform(put(PATH)
                .queryParam("id", String.valueOf(1L))
                .content(objectMapper.writeValueAsString(data))
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @DisplayName("should return badrequest for invalid parameters passed to dto")
    void returnBadRequestByDto2() throws Exception {

        CourtUpdateDto data = new CourtUpdateDto(
                "Nome",
                CourtAvailabilityStatusEnum.AVAILABLE
        );

        SecurityContextUtils.fakeAuthentication(List.of(PropertyTestConstants.ROLE_ADMIN));

        ResultActions resultActions = mockMvc.perform(put(PATH)
                .queryParam("id", (String) null)
                .content(objectMapper.writeValueAsString(data))
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @DisplayName("Should return Forbiddenpost. Code: 403.")
    void returnForbidden() throws Exception {
        CourtUpdateDto newData = new CourtUpdateDto(
                "Nome quadra",
                CourtAvailabilityStatusEnum.AVAILABLE
        );

        SecurityContextUtils.fakeAuthentication(List.of(PropertyTestConstants.ROLE_USER));
        callEndpoint(newData, 1L).andExpect(status().isForbidden()).andReturn();
    }

    private ResultActions callEndpoint(CourtUpdateDto data, Long id) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .put(PATH)
                .queryParam("id", String.valueOf(id))
                .content(objectMapper.writeValueAsString(data))
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );
    }
}
