package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;

public interface ReservationService extends BasicService<Reservation, Long> {
    List<Reservation> findByCourtAndDateTime(Long courtId, LocalDateTime date);

    List<Reservation> findByUserId(Long userId);

    Reservation createReservation(Long userId, Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    void deleteReservation(Long reservationId, Long userId);
}
