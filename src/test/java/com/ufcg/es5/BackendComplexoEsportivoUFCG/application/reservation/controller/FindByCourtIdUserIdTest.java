package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.controller.BasicTestController;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.ReservationPathConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.global.PropertyConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security.SecurityContextUtils;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
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

class FindByCourtIdUserIdTest extends BasicTestController {

    @MockBean
    private ReservationService reservationService;

    @ParameterizedTest
    @DisplayName("Should return Success. Code: 200")
    @MethodSource(value = "returnSuccess")
    void returnSuccess(List<String> roles) throws Exception {
        SecurityContextUtils.fakeAuthentication(roles);
        Collection<ReservationResponseDto> response = makeResponse();

        Mockito.when(reservationService.findByCourtIdAndUserId(VALID_ID, VALID_ID)).thenReturn(response);

        callEndpoint(VALID_ID, VALID_ID).andExpect(status().isOk()).andReturn();
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
    void returnBadRequest(Long courtId, Long userId) throws Exception {
        SecurityContextUtils.fakeAuthentication(List.of(ROLE_ADMIN));

        callEndpoint(courtId, userId).andExpect(status().isBadRequest()).andReturn();
    }

    private static Stream<Arguments> returnBadRequest() {
        return Stream.of(
                Arguments.of(null, 1L),
                Arguments.of(1L, null)
        );
    }

    @ParameterizedTest
    @DisplayName("Should return Forbidden. Code: 403.")
    @MethodSource(value = "returnForbidden")
    void returnForbidden(Collection<String> roles) throws Exception {
        SecurityContextUtils.fakeAuthentication(roles);
        callEndpoint(VALID_ID, VALID_ID).andExpect(status().isForbidden()).andReturn();
    }

    private static Stream<Arguments> returnForbidden() {
        return Stream.of(
                Arguments.of(List.of(ROLE_USER)),
                Arguments.of(List.of(ROLE_PENDING))
        );
    }

    private ResultActions callEndpoint(Long courtId, Long userId) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .get(ReservationPathConstants.FIND_BY_COURT_ID_USER_ID_FULL_PATH)
                .param(PropertyConstants.COURT_ID, Objects.toString(courtId, null))
                .param(PropertyConstants.USER_ID, Objects.toString(userId, null))
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
