package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Stream;

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

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security.SecurityContextUtils;

import io.swagger.v3.oas.annotations.Parameter;

@SpringBootTest
public class FindByUserId extends BasicTestController{

    public static final long VALID_ID = 1L;
    private static final String ID_PROPERTY = "id";
    private static final String PATH_BY_USERID = "/reservation/by/id";
    private static final String PATH_BY_COURT_AND_DATE = "/reservation/by/court";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_INVALID = "ROLE_INVALID";

    @MockBean
    private ReservationService reservationService;

    
    private static Stream<Arguments> returnSuccess() {
        return Stream.of(
                Arguments.of(List.of(ROLE_USER)),
                Arguments.of(List.of(ROLE_ADMIN)),
                Arguments.of(List.of(ROLE_USER, ROLE_USER))
        );
    }

    
    @BeforeEach
    void setUp() {
        Mockito.doNothing().when(reservationService).findByUserId(VALID_ID);
    }

    @ParameterizedTest
    @DisplayName("Should return Success. Code: 200")
    @MethodSource(value = "returnSuccess")
    void returnSuccess(List<String> roles) throws Exception {
        SecurityContextUtils.fakeAuthentication(roles);
        callEndpoint().andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("Should return BadRequest. Code: 400")
    void returnBadRequest() throws Exception {
        SecurityContextUtils.fakeAuthentication(List.of(ROLE_USER));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_BY_USERID)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isBadRequest()).andReturn();
    }
    
    @Test
    @DisplayName("Should return Forbidden. Code: 403")
    @MethodSource(value = "returnForbidden")
    void returnForbidden() throws Exception {
        SecurityContextUtils.fakeAuthentication(List.of(ROLE_INVALID));
        callEndpoint().andExpect(status().isForbidden()).andReturn();
    }

    private ResultActions callEndpoint() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_BY_USERID)
                .queryParam(ID_PROPERTY, String.valueOf(VALID_ID))
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );
    }
}
