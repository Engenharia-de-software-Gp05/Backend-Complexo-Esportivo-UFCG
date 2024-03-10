package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository.ReservationRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository repository;

    @Override
    public JpaRepository<Reservation, Long> getRepository() {
        return repository;
    }

    @Override
    public List<Reservation> findByCourtByDate(Long courtId, String date) {
        return repository.findByCourtByDate(courtId, date);
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    } 
}
