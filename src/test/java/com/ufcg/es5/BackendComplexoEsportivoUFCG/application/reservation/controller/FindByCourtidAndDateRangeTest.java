package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
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
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.PropertyTestConstants.*;
import static com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller.DeleteTest.VALID_ID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FindByCourtidAndDateRangeTest extends BasicTestController {
    @MockBean
    private ReservationService reservationService;

    @ParameterizedTest
    @DisplayName("Should return Success. Code: 200")
    @MethodSource(value = "returnSuccess")
    void returnSuccess(List<String> roles) throws Exception {
        SecurityContextUtils.fakeAuthentication(roles);
        Collection<ReservationResponseDto> response = makeResponse();

        Mockito.when(reservationService.findByCourtIdAndUserId(VALID_ID, VALID_ID)).thenReturn(response);

        callEndpoint(VALID_ID, "2024-01-01 12:00:00", "2024-01-01 13:00:00").andExpect(status().isOk()).andReturn();
    }

    private static Stream<Arguments> returnSuccess() {
        return Stream.of(
                Arguments.of(List.of(ROLE_ADMIN)),
                Arguments.of(List.of(ROLE_ADMIN, ROLE_USER))
        );
    }

    @ParameterizedTest
    @MethodSource(value = "returnBadRequest")
    @DisplayName("Should return BadRequest. Code: 400")
    void returnBadRequest(Long courtId, String startDateTime, String endDateTime) throws Exception {
        SecurityContextUtils.fakeAuthentication(List.of(ROLE_ADMIN));

        callEndpoint(courtId, startDateTime, endDateTime).andExpect(status().isBadRequest()).andReturn();
    }

    private static Stream<Arguments> returnBadRequest() {
        return Stream.of(
                Arguments.of(null, "2024-01-01 10:00:00", "2024-01-01 12:00:00"),
                Arguments.of(1L, "", "2024-01-01 12:00:00"),
                Arguments.of(1L, null, "2024-01-01 12:00:00"),
                Arguments.of(1L, "2024-01-01 12:00:00", ""),
                Arguments.of(1L, "2024-01-01 12:00:00", null)
        );
    }

    @Test
    @DisplayName("Should return Forbidden. Code: 403.")
    void returnForbidden() throws Exception {
        SecurityContextUtils.fakeAuthentication(List.of(ROLE_PENDING));
        callEndpoint(VALID_ID, "2024-01-01 12:00:00", "2024-01-01 13:00:00").andExpect(status().isForbidden()).andReturn();
    }


    private ResultActions callEndpoint(Long courtId, String startDateTime, String endDateTime) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .get(ReservationPathConstants.FIND_BY_COURT_ID_AND_DATE_RANGE_FULL_PATH)
                .param(PropertyConstants.COURT_ID, Objects.toString(courtId, null))
                .param(PropertyConstants.START_DATE_TIME, startDateTime)
                .param(PropertyConstants.END_DATE_TIME, endDateTime)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON)
        );
    }

    private Collection<ReservationResponseDto> makeResponse() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return List.of(new ReservationResponseDto(
                1L,
                localDateTime,
                localDateTime
        ));
    }
}
