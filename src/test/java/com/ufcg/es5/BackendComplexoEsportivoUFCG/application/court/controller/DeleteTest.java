package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.PropertyTestConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security.SecurityContextUtils;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class DeleteTest extends BasicTestController {

    public static final long VALID_ID = 1L;
    private static final String PATH = "/court/delete/by/id";

    @MockBean
    private CourtService courtService;

    @BeforeEach
    void setUp() {
        Mockito.doNothing().when(courtService).deleteById(VALID_ID);
    }

    @ParameterizedTest
    @DisplayName("Should return Success. Code: 204")
    @MethodSource(value = "returnNoContent")
    void returnNoContent(List<String> roles) throws Exception {
        SecurityContextUtils.fakeAuthentication(roles);
        callEndpoint().andExpect(status().isNoContent()).andReturn();
    }

    private static Stream<Arguments> returnNoContent() {
        return Stream.of(
                Arguments.of(List.of(PropertyTestConstants.ROLE_ADMIN)),
                Arguments.of(List.of(PropertyTestConstants.ROLE_ADMIN, PropertyTestConstants.ROLE_PENDING))
        );
    }

    @Test
    @DisplayName("Should return BadRequest ")
    void returnBadRequestByQueryParam() throws Exception {
        SecurityContextUtils.fakeAuthentication(List.of(PropertyTestConstants.ROLE_ADMIN));

        ResultActions resultActions = mockMvc.perform(delete(PATH)
                .queryParam(PropertyTestConstants.ID, (String) null)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @DisplayName("Should return BadRequest ")
    void returnBadRequest() throws Exception {
        SecurityContextUtils.fakeAuthentication(List.of(PropertyTestConstants.ROLE_ADMIN));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete(PATH)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @DisplayName("Should return Forbidden. Code: 403.")
    void returnForbidden() throws Exception {
        SecurityContextUtils.fakeAuthentication(List.of(PropertyTestConstants.ROLE_PENDING));
        callEndpoint().andExpect(status().isForbidden()).andReturn();
    }

    private ResultActions callEndpoint() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .delete(PATH)
                .queryParam(PropertyTestConstants.ID, String.valueOf(VALID_ID))
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );
    }
}
