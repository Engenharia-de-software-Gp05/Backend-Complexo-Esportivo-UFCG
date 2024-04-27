package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Reservation.ReservationDetailedProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.Reservation.ReservationResponseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
                SELECT reservation.id as id,
                       reservation.startDateTime as startDateTime,
                       reservation.endDateTime as endDateTime
                    FROM Reservation reservation
                        WHERE reservation.court.id = :courtId AND
                            reservation.saceUser.id = :userId AND
                            reservation.startDateTime >= :dateTime
            """
    )
    Collection<ReservationResponseProjection> findByCourtIdUserIdAndDateTime(
            @Param("courtId") Long courtId,
            @Param("userId") Long userId,
            @Param("dateTime") LocalDateTime dateTime
    );

    @Query(
        """
                SELECT reservation
                FROM Reservation reservation
                WHERE reservation.court.id = :courtId
        """
    )
    Collection<ReservationResponseProjection> findByCourtId(
            @Param("courtId") Long courtId
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

    @Query(
            """
                    SELECT reservation.id as id,
                           reservation.startDateTime as startDateTime,
                           reservation.endDateTime as endDateTime
                        FROM Reservation reservation
                            WHERE reservation.court.id = :courtId AND
                                ((reservation.startDateTime > :startDateTime AND reservation.startDateTime < :endDateTime)
                                OR
                                (reservation.endDateTime > :startDateTime AND reservation.endDateTime < :endDateTime))
                    """
    )
    Collection<ReservationResponseProjection> findByCourtIdAndTimeInterval(
            @Param("courtId") Long courtId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);

    @Query(
            """
                    SELECT reservation.id as id,
                           reservation.startDateTime as startDateTime,
                           reservation.endDateTime as endDateTime
                        FROM Reservation reservation
                            WHERE reservation.saceUser.id = :userId AND
                                reservation.startDateTime = :startDateTime
                    """
    )
    Collection<ReservationResponseProjection> findByUserIdAndStartDateTime(
            @Param("userId") Long userId,
            @Param("startDateTime") LocalDateTime startDateTime);

    @Query(
            """
                    SELECT reservation.id as id,
                           reservation.startDateTime as startDateTime,
                           reservation.endDateTime as endDateTime
                        FROM Reservation reservation
                            WHERE reservation.court.id = :courtId AND
                                reservation.saceUser.id = :userId AND
                                ((reservation.startDateTime > :startDateTime AND reservation.startDateTime < :endDateTime)
                                OR
                                (reservation.endDateTime > :startDateTime AND reservation.endDateTime < :endDateTime))
                    """
    )
    Collection<ReservationResponseProjection> findByCourtIdUserIdAndTimeInterval(
            @Param("courtId") Long courtId,
            @Param("userId") Long userId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);

    @Query(
            """
            SELECT reservation.court.name as courtName,
                    reservation.saceUser.name as userName,
                    reservation.startDateTime as startDateTime,
                    reservation.endDateTime as endDateTime
                FROM Reservation reservation
                    WHERE reservation.saceUser.id = :userId AND
                        reservation.startDateTime > :dateTime
            """
    )
    Collection<ReservationDetailedProjection> findDetailedByUserIdAndDateTime(
            @Param("userId") Long id,
            @Param("dateTime") LocalDateTime dateTime
    );

    @Query(
            """
            SELECT reservation.court.name as courtName,
                    reservation.saceUser.name as userName,
                    reservation.startDateTime as startDateTime,
                    reservation.endDateTime as endDateTime
                FROM Reservation reservation
            """
    )
    Collection<ReservationDetailedProjection> findAllDetailed();
}
