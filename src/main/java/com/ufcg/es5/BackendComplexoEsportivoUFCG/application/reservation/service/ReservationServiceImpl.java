package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository.ReservationRepository;
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
}
