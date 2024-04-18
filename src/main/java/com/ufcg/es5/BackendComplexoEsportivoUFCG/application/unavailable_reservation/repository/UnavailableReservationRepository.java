package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.unavailable_reservation.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.UnavailableReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnavailableReservationRepository extends JpaRepository<UnavailableReservation, Long> {
}
