package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.unavailable_reservation.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.unavailable_reservation.UnavailableReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.UnavailableReservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.UnavailableReservationResponseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface UnavailableReservationRepository extends JpaRepository<UnavailableReservation, Long> {
    @Query(
            """
                    SELECT unavailableReservation.id as id,
                                   unavailableReservation.startDateTime as startDateTime,
                                   unavailableReservation.endDateTime as endDateTime
                               FROM UnavailableReservation unavailableReservation
                                   WHERE unavailableReservation.court.id = :courtId AND
                                       unavailableReservation.startDateTime >= :startDateTime AND
                                       unavailableReservation.endDateTime <= :endDateTime
            """
    )
    Collection<UnavailableReservationResponseProjection> findByCourtIdAndDateRange(
            @Param("courtId") Long courtId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);

    @Query(
            """
                    SELECT unavailableReservation
                        FROM UnavailableReservation unavailableReservation
                            WHERE unavailableReservation.court.id = :courtId AND
                                unavailableReservation.startDateTime = :startDateTime
            """
    )
    Optional<UnavailableReservation> findByCourtIdAndStartDateTime(
            @Param("courtId") Long courtId,
            @Param("startDateTime") LocalDateTime startDateTime);
}
