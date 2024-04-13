package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.PropertyConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.constants.ReservationAtributesConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.constants.ReservationAtributesConstants.*;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.constants.ReservationPathConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security.SecurityContextUtils;

import io.swagger.v3.oas.annotations.Parameter;

@SpringBootTest
public class CreateReservationTest extends BasicTestController{

    @MockBean
    private ReservationService reservationService;

    @ParameterizedTest
    @DisplayName("Should return Sucess. Code :200")
    @MethodSource("returnSuccess")
    void returnSuccess(Long userId) throws Exception{
        //TODO
    }


    @ParameterizedTest
    @DisplayName("Should return Bad Request. Code :400")
    @MethodSource("returnBadRequest")
    void returnBadRequest(Long userId) throws Exception {
        //TODO
    }

    private static Stream<Arguments> returnSuccess() {
        return Stream.of(
                Arguments.of(),
                Arguments.of(),
                Arguments.of()
        );
    }

    private static Stream<Arguments> returnBadRequest() {
        return Stream.of(
                Arguments.of(),
                Arguments.of(),
                Arguments.of()
        );
    }

    private void makeResponse(){
        //TODO
    }

    private String makeRequestPayload(Long userId) throws JsonProcessingException {
        Map<String, Object> payload = new HashMap<>();
        payload.put(PropertyConstants.ID, String.valueOf(userId));

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(payload);
    }

    private ResultActions callEndPoint() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .get(ReservationPathConstants.CREATE_FULL_PATH)
                .header(PropertyConstants.ID, ReservationAtributesConstants.VALID_ID)
                .header(HttpHeaders.CONTENT_TYPE, 
                        MediaType.APPLICATION_JSON_VALUE)
        );
    }
  
}
