package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationDetailedDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface ReservationService extends BasicService<Reservation, Long> {
    Collection<ReservationResponseDto> findByCourtIdAndDateRange(Long courtId, LocalDateTime startDate, LocalDateTime endDate);

    Collection<ReservationResponseDto> findByCourtIdUserId(Long courtId, Long userId);

    ReservationResponseDto create(ReservationSaveDto reservationSaveDto);

    void delete(Long reservationId);

    void deleteByIdAndMotive(Long id, String motive);

    Boolean existsByCourtIdAndTimeInterval(Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Boolean existsByUserIdAndStartDateTime(Long userId, LocalDateTime startDateTime);

    Boolean existsByCourtIdUserIdAndTimeInterval(Long courtId, Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Collection<ReservationResponseDto> findByCourtId(Long courtId);

    Collection<ReservationResponseDto> findByCourtIdAndTimeInterval(Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Collection<ReservationResponseDto> findByUserIdAndStartDateTime(Long userId, LocalDateTime startDateTime);

    Collection<ReservationResponseDto> findByCourtIdUserIdAndTimeInterval(Long courtId, Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Optional<Reservation> findByCourtIdAndStartDateTime(Long courtId, LocalDateTime startDateTime);

    Collection<ReservationDetailedDto> findDetailedByAuthenticatedUser();

    Collection<ReservationDetailedDto> findAllDetailed();
}

