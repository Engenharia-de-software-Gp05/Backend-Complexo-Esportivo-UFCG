package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    Optional<Reservation> findById(
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
    List<Reservation> findByCourtAndDateTime(
            @Param("courtId") Long courtId,
            @Param("date") LocalDateTime date
    );



}
