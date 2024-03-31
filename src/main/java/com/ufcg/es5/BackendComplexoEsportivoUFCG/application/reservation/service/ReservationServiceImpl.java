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
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceConflictException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceForbiddenException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.sace_user.SaceUserExceptionMessages;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.court.CourtExceptionMessages;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.reservation.ReservationExeceptionMessages;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Transactional
    public ReservationResponseDto findByCourtAndStartDateTime(Long courtId, LocalDateTime date) throws SaceResourceNotFoundException {
        courtService.findById(courtId).orElseThrow(() -> new SaceResourceNotFoundException(
                CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(courtId)
        ));
        return this.repository.findByCourtAndStartDateTime(courtId, date);
    }

    @Override
    @Transactional
    public Collection<ReservationResponseDto> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Reservation createReservation(ReservationSaveDto reservationSaveDto) throws
            SaceResourceNotFoundException,
            SaceConflictException
    {
        Long userId = authenticatedUser.getAuthenticatedUserId();
        SaceUser user = saceUserService.findById(userId).orElseThrow();

        Court court = courtService.findById(reservationSaveDto.courtId()).orElseThrow(() -> new SaceResourceNotFoundException(
                CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(reservationSaveDto.courtId())
        ));

        checkTimeAvailability(reservationSaveDto);

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
                court, null,
                ReservationAvailabilityStatusEnum.UNAVAILABLE
        );

        return repository.save(reservation);
    }


    @Override
    @Transactional
    public void deleteById(Long id) throws SaceResourceNotFoundException, SaceForbiddenException {
        Reservation reservation = repository.findById(id).orElseThrow(() -> new SaceResourceNotFoundException(
                ReservationExeceptionMessages.RESERVATION_WITH_ID_NOT_FOUND.formatted(id)
        ));
        Long userId = authenticatedUser.getAuthenticatedUserId();
        checkPermission(userId, reservation);
        repository.delete(reservation);
    }

    private void checkPermission(Long userId, Reservation reservation) {
        if (!isOwner(userId, reservation) && !authenticatedUser.hasRole(SaceUserRoleEnum.ROLE_ADMIN)) {
            throw new SaceForbiddenException(
                    ReservationExeceptionMessages.RESERVATION_PERMISSION_DENIED
            );
        }
    }

    @Override
    @Transactional
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
        return new Reservation(
                reservationSaveDto.startDateTime(),
                reservationSaveDto.endDateTime(),
                court,
                user,
                status
        );
    }

    private void checkTimeAvailability(ReservationSaveDto reservationSaveDto) {
        if (
                repository.findByCourtAndStartDateTime(reservationSaveDto.courtId(), reservationSaveDto.startDateTime()) != null ||
                repository.findByCourtAndEndtDateTime(reservationSaveDto.courtId(), reservationSaveDto.endDateTime()) != null
        ) {
            throw new SaceConflictException(ReservationExeceptionMessages.RESERVATION_TIME_CONFLICT);
        }
    }

}