package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.PropertyTestConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.ReservationPathConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.global.PropertyConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security.SecurityContextUtils;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller.DeleteTest.VALID_ID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateTest extends BasicTestController {

    @MockBean
    private ReservationService reservationService;

    @ParameterizedTest
    @DisplayName("Should return Created. Code: 201")
    @MethodSource(value = "returnCreated")
    void returnCreated(Long courtId, String startDateTime) throws Exception {
        String payload = makeRequestPayload(courtId, startDateTime);
        ReservationResponseDto response = makeResponse();

        Mockito.when(reservationService.create(Mockito.any()))
                .thenReturn(response);

        callEndpoint(payload).andExpect(status().isCreated()).andReturn();
    }

    @ParameterizedTest
    @DisplayName("Should return BadRequest ")
    @MethodSource(value = "returnBadRequest")
    void returnBadRequest(Long courtId, String startDateTime) throws Exception {
        String payload = makeRequestPayload(courtId, startDateTime);

        callEndpoint(payload).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @DisplayName("Should return Forbidden. Code: 403.")
    void returnForbidden() throws Exception {
        SecurityContextUtils.fakeAuthentication(List.of(PropertyTestConstants.ROLE_PENDING));

        String payload = makeRequestPayload(VALID_ID, "2024-11-01 12:00");

        callEndpoint(payload).andExpect(status().isForbidden()).andReturn();
    }

    private static Stream<Arguments> returnCreated() {
        return Stream.of(
                Arguments.of(1L, "2024-11-01 12:00")
        );
    }

    private static Stream<Arguments> returnBadRequest() {
        return Stream.of(
                Arguments.of(1L, ""),
                Arguments.of(null, "2024-12-01 12:00"),
                Arguments.of(1L, null)
        );
    }

    private String makeRequestPayload(Long courtId, String startDateTime) throws JsonProcessingException {
        Map<String, Object> payload = new HashMap<>();

        payload.put(PropertyConstants.COURT_ID, courtId);
        payload.put(PropertyConstants.START_DATE_TIME, startDateTime);

        return objectMapper.writeValueAsString(payload);
    }

    private ReservationResponseDto makeResponse() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return new ReservationResponseDto(
                1L,
                localDateTime,
                localDateTime
        );
    }

    private ResultActions callEndpoint(String payload) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post(ReservationPathConstants.CREATE_FULL_PATH)
                .content(payload)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );
    }
}
