package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.PropertyTestConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.ReservationPathConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DeleteTest extends BasicTestController {

    public static final long VALID_ID = 1L;

    @MockBean
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        Mockito.doNothing().when(reservationService).delete(VALID_ID);
    }

    @ParameterizedTest
    @DisplayName("Should return Success. Code: 200")
    @MethodSource(value = "returnNoContent")
    void returnNoContent(List<String> roles) throws Exception {
        SecurityContextUtils.fakeAuthentication(roles);
        callEndpoint().andExpect(status().isNoContent()).andReturn();
    }

    private static Stream<Arguments> returnNoContent() {
        return Stream.of(
                Arguments.of(List.of(PropertyTestConstants.ROLE_USER)),
                Arguments.of(List.of(PropertyTestConstants.ROLE_ADMIN)),
                Arguments.of(List.of(PropertyTestConstants.ROLE_ADMIN, PropertyTestConstants.ROLE_PENDING))
        );
    }

    @Test
    @DisplayName("Should return BadRequest ")
    void returnBadRequest() throws Exception {
        SecurityContextUtils.fakeAuthentication(List.of(PropertyTestConstants.ROLE_USER));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete(ReservationPathConstants.DELETE_BY_ID_FULL_PATH)
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
                .delete(ReservationPathConstants.DELETE_BY_ID_FULL_PATH)
                .queryParam(PropertyTestConstants.ID, String.valueOf(VALID_ID))
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );
    }

}
