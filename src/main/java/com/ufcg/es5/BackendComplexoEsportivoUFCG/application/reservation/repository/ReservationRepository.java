package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.global.PropertyConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.ReservationResponseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
                SELECT reservation
                FROM Reservation reservation
                WHERE reservation.saceUser.id = :saceUserId
            """
    )
    Collection<ReservationResponseDto> findByUserId(
            @Param("saceUserId") Long saceUserId
    );

    @Query("""
                SELECT reservation
                FROM Reservation reservation
                WHERE reservation.court.id = :courtId
                AND reservation.startDateTime = :date
            """
    )
    Collection<ReservationResponseDto> findByCourtAndDateTime(
            @Param("courtId") Long courtId,
            @Param("date") LocalDateTime date
    );

    @Query(
            """
             SELECT reservation.id as id,
                    reservation.startDateTime as startDateTime,
                    reservation.endDateTime as endDateTime
                FROM Reservation reservation
                    WHERE reservation.court.id = :courtId AND
                        reservation.startDateTime >= :startDateTime AND
                        reservation.endDateTime <= :endDateTime
             """
    )
    Collection<ReservationResponseProjection> findByCourtIdAndDateRange(
            @Param("courtId") Long courtId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);
}
