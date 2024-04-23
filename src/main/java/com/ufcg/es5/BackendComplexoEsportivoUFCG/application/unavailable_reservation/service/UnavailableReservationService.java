package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.unavailable_reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.unavailable_reservation.UnavailableReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.UnavailableReservation;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface UnavailableReservationService extends BasicService<UnavailableReservation, Long> {

    Collection<UnavailableReservationResponseDto> findByCourtIdAndDateRange(Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Optional<UnavailableReservation> findByCourtIdAndStartDateTime(Long courtId, LocalDateTime startDateTime);

    UnavailableReservationResponseDto create(ReservationSaveDto reservationSaveDto);

    void delete(Long id);
}
