package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

import java.util.List;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.basic.BasicService;

public interface ReservationService extends BasicService<Reservation, Long> {
    List<Reservation> findByCourtByDate(Long courtId, String date);

    List<Reservation> findByUserId(Long userId);

    void deleteReservation(Long reservationId, Long userId);
}
