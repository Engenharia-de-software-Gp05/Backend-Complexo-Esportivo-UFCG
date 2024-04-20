package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ReservationService extends BasicService<Reservation, Long> {
    Collection<ReservationResponseDto> findByCourtIdAndDateRange(Long courtId, LocalDateTime startDate, LocalDateTime endDate);

    Collection<ReservationResponseDto> findByCourtIdUserId(Long coutId, Long userId);

    Reservation createReservation(ReservationSaveDto reservationSaveDto);

    void delete(Long reservationId);

    void adminDeleteById(Long id);

    Boolean existsByCourtIdAndTimeInterval(Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Boolean existsByUserIdAndStartDateTime(Long userId, LocalDateTime startDateTime);

    Boolean existsByCourtIdUserIdAndTimeInterval(Long courtId, Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Collection<ReservationResponseDto> findByCourtIdAndTimeInterval(Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Collection<ReservationResponseDto> findByUserIdAndStartDateTime(Long userId, LocalDateTime startDateTime);

    Collection<ReservationResponseDto> findByCourtIdUserIdAndTimeInterval(Long courtId, Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}

