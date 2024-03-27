package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository.ReservationRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.enums.ReservationAvailabilityStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private SaceUserService saceUserService;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private CourtService courtService;

    @Override
    public JpaRepository<Reservation, Long> getRepository() {
        return repository;
    }

    @Override
    public Collection<ReservationResponseDto> findByCourtAndDateTime(Long courtId, LocalDateTime date) {
        return null;
    }

    @Override
    public Collection<ReservationResponseDto> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Reservation createReservation(ReservationSaveDto reservationSaveDto) {
        Long userId = authenticatedUser.getAuthenticatedUserId();
        SaceUser user = saceUserService.findById(userId).orElseThrow();

        Court court = courtService.findById(reservationSaveDto.courtId()).orElseThrow();

        Reservation reservation = makeReservation(
                reservationSaveDto,
                court,
                user,
                ReservationAvailabilityStatusEnum.BOOKED
        );

        return repository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation makeUnavailable(ReservationSaveDto reservationMakeUnavailableDto) {
        Court court = courtService
                .findById(reservationMakeUnavailableDto.courtId())
                .orElseThrow();

        Reservation reservation = makeReservation(
                reservationMakeUnavailableDto,
                court, 
                null,
                ReservationAvailabilityStatusEnum.UNAVAILABLE
        );

        return repository.save(reservation);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        Reservation reservation = repository.findById(id).orElseThrow(
                SaceResourceNotFoundException::new
        );

        Long userId = authenticatedUser.getAuthenticatedUserId();
        if (isOwner(userId, reservation)) {
            repository.delete(reservation);
        } else {
            throw new IllegalArgumentException("User has no permission to delete the reservation.");
        }
    }

    @Override
    public void adminDeleteById(Long id) {
        Reservation reservation = repository.findById(id).orElseThrow();
        repository.delete(reservation);
    }

    private boolean isOwner(Long userId, Reservation reservation) {
        return reservation.getSaceUser().getId().equals(userId);
    }

    private Reservation makeReservation(
            ReservationSaveDto reservationSaveDto,
            Court court,
            SaceUser user,
            ReservationAvailabilityStatusEnum status
    ) {
        if(user != null){
            LocalDateTime startDateTime = reservationSaveDto.startDateTime().plusDays(-1*(court.getMinimumTimeForOtherReservation()));
            LocalDateTime endDateTime = reservationSaveDto.endDateTime().plusDays(court.getMinimumTimeForOtherReservation());
            Long userId = authenticatedUser.getAuthenticatedUserId();
    
            checkByDate(reservationSaveDto.startDateTime(), court.getId(), userId);
            checkByCourtAndDateTimeRange(startDateTime, endDateTime, court.getId(), userId);
        }
        
        return new Reservation(
                reservationSaveDto.startDateTime(),
                reservationSaveDto.endDateTime(),
                court,
                user,
                status
        );
    }

    private void checkByCourtAndDateTimeRange(LocalDateTime startDateTime, LocalDateTime endDateTime, Long courtId, Long userId) {
        if (existByDateRange(startDateTime, endDateTime, courtId, userId)) {
            throw new RuntimeException("Reservation already exists for this date range. You have to wait for the minimum time between reservations.");
        }
    }

    @Override
    public Boolean existByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime, Long courtId, Long userId) {
        return repository.findByCourtAndDateTimeRange(startDateTime, endDateTime, courtId, userId) != null;
    }


    private void checkByDate(LocalDateTime startDateTime, Long courtId, Long userId) {
        if (this.existByDate(startDateTime, courtId, userId)) {
            throw new RuntimeException("Reservation already exists for this date. You have to wait for the minimum time between reservations.");
        }
    }

    @Override
    public Boolean existByDate(LocalDateTime startDateTime, Long courtId, Long userId) {
        return repository.existByDate(startDateTime, courtId, userId);
    }

}