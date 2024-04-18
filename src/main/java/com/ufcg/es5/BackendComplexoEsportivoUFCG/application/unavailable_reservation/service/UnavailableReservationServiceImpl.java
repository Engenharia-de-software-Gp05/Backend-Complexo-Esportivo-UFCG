package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.unavailable_reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.UnavailableReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UnavailableReservationServiceImpl implements UnavailableReservationService {
    @Override
    public JpaRepository<UnavailableReservation, Long> getRepository() {
        return null;
    }
}
