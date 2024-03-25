package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
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
        // i want a sql query that get all reservations from a court that are from a specific user and verify if their startDateTime plus the minimumTimeForOtherReservation is less than the startDateTime of the new reservation
        
        @Query("""
                SELECT reservation
                FROM Reservation reservation
                WHERE reservation.court.id = :courtId
                AND reservation.saceUser.id = :userId
                AND reservation.startDateTime > :maxDateBefore OR reservation.startDateTime < :minDateAfter

            """
        )
        Collection<ReservationResponseDto> findByCourtAndDateTimeRange(
                @Param("maxDateBefore") LocalDateTime maxDateBefore,
                @Param("minDateAfter") LocalDateTime minDateAfter,
                @Param("courtId") Long courtId,
                @Param("userId") Long userId
        );

        @Query("""
                SELECT CASE WHEN COUNT(reservation) > 0 THEN TRUE ELSE FALSE END
                FROM Reservation reservation
                WHERE reservation.startDateTime = :startDateTime
                AND reservation.court.id = :courtId
                AND reservation.saceUser.id = :userId
            """
        )
        boolean existByDate(
                @Param("startDateTime") LocalDateTime startDateTime,
                @Param("courtId") Long courtId,
                @Param("userId") Long userId
        );
        
}
