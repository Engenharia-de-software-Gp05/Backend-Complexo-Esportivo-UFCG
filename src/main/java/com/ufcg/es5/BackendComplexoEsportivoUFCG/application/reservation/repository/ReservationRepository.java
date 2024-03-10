package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
        SELECT reservation
        FROM Reservation reservation
        WHERE reservation.id = :id
    """
    )
    Reservation findReservationById(
            @Param("id") Long id
    );

    @Query("""
        SELECT reservation
        FROM Reservation reservation
        WHERE reservation.user.id = :userId
    """
    )
    List<Reservation> findByUserId(Long userId);
    
    @Query("""
        SELECT reservation
        FROM Reservation reservation
        WHERE reservation.court.id = :courtId
        AND reservation.date = :date
    """
    )
    List<Reservation> findByCourtByDate(
            @Param("courtId") Long courtId,
            @Param("date") String date
    );



}
