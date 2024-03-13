package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ReservationService extends BasicService<Reservation, Long> {
    Collection<ReservationResponseDto> findByCourtAndDateTime(Long courtId, LocalDateTime date);

    Collection<ReservationResponseDto> findByUserId(Long userId);

    Reservation createReservation(Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Reservation makeUnavailable(Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    void deleteReservation(Long reservationId);


}
