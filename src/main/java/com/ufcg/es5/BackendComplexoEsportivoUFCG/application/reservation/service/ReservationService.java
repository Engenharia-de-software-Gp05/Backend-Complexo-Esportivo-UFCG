package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ReservationService extends BasicService<Reservation, Long> {
    Collection<ReservationResponseDto> findByCourtAndDateTime(Long courtId, LocalDateTime date);

    Collection<ReservationResponseDto> findByUserId(Long userId);

    Reservation createReservation(ReservationSaveDto reservationSaveDto);

    Reservation makeUnavailable(ReservationSaveDto reservationMakeUnavailableDto);

    void deleteReservation(Long reservationId);

    Boolean existByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime, Long courtId, Long userId);
    
    Boolean existByDate(LocalDateTime startDateTime, Long courtId, Long userId);
}
