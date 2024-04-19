package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ReservationService extends BasicService<Reservation, Long> {
    Collection<ReservationResponseDto> findByCourtIdAndDateRange(Long courtId, LocalDateTime startDate, LocalDateTime endDate);

    Collection<ReservationResponseDto> findByUserId(Long userId);

    Reservation createReservation(ReservationSaveDto reservationSaveDto);

    void deleteById(Long reservationId);

    void adminDeleteById(Long id);
}
