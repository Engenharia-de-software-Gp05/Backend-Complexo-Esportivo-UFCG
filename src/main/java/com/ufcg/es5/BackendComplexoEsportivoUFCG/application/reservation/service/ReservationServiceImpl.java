package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository.ReservationRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.enums.ReservationAvailabilityStatusEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceConflictException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceForbiddenException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
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
    public ReservationResponseDto findByCourtIdAndStartDateTime(Long courtId, LocalDateTime date) throws SaceResourceNotFoundException {
        courtService.findById(courtId).orElseThrow(() -> new SaceResourceNotFoundException(
                CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(courtId)
        ));
        return this.repository.findByCourtIdAndStartDateTime(courtId, date);
    }

    @Override
    @Transactional
    public ReservationResponseDto findByCourtIdAndEndtDateTime(Long courtId, LocalDateTime date) throws SaceResourceNotFoundException {
        courtService.findById(courtId).orElseThrow(() -> new SaceResourceNotFoundException(
                CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(courtId)
        ));
        return this.repository.findByCourtIdAndEndtDateTime(courtId, date);
    }

    @Override
    @Transactional
    public Collection<ReservationResponseDto> findByCourtIdAndTimeInterval(
            Long courtId,
            LocalDateTime startDataTime,
            LocalDateTime endDataTime
    ) throws SaceResourceNotFoundException {
        courtService.findById(courtId).orElseThrow(() -> new SaceResourceNotFoundException(
                CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(courtId)
        ));
        return this.repository.findByCourtIdAndTimeInterval(courtId, startDataTime, endDataTime);
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

        return this.save(reservation);
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
        Reservation reservation = this.findById(id).orElseThrow(() -> new SaceResourceNotFoundException(
                ReservationExeceptionMessages.RESERVATION_WITH_ID_NOT_FOUND.formatted(id)
        ));
        Long userId = authenticatedUser.getAuthenticatedUserId();
        checkIfReservationBelongsToUser(userId, reservation);
        checkCancellationTimeLimit(reservation);
        this.deleteById(reservation.getId());
    }
  
    @Override
    @Transactional
    public void adminDeleteById(Long id) throws SaceResourceNotFoundException {
        Reservation reservation = repository.findById(id).orElseThrow(() -> new SaceResourceNotFoundException(
                ReservationExeceptionMessages.RESERVATION_WITH_ID_NOT_FOUND.formatted(id)
        ));
        repository.delete(reservation);
    }

    @Override
    public Boolean existsByCourtIdAndStartDataTime(Long courtid, LocalDateTime localDateTime) {
        return findByCourtIdAndStartDateTime(courtid, localDateTime) != null;
    }

    @Override
    public Boolean existsByCourtIdAndEndDataTime(Long courtId, LocalDateTime localDateTime) {
        return findByCourtIdAndEndtDateTime(courtId, localDateTime) != null;
    }

    @Override
    public Boolean existsByCourtIdAndTimeInterval(Long courtId, LocalDateTime startDataTime, LocalDateTime endDataTime) {
        return !findByCourtIdAndTimeInterval(courtId, startDataTime, endDataTime).isEmpty();
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
  
    private void checkPermission(Long userId, Reservation reservation) {
        if (!isOwner(userId, reservation)) {
            throw new SaceForbiddenException(
                    ReservationExeceptionMessages.RESERVATION_PERMISSION_DENIED
            );
        }
    }

    private void checkTimeAvailability(ReservationSaveDto data) {
        if (existsByCourtIdAndTimeInterval(data.courtId(), data.startDateTime(), data.endDateTime())) {
            throw new SaceConflictException(ReservationExeceptionMessages.RESERVATION_TIME_CONFLICT
                    .formatted(data.startDateTime(), data.endDateTime()));
        }
    }

    private void checkCancellationTimeLimit(Reservation reservation) {
        LocalDateTime now = LocalDateTime.now();

        if (reservation.getStartDateTime().isBefore(now.plusHours(24))) {
            throw new SaceForbiddenException(
                    ReservationExeceptionMessages.RESERVATION_CANCELLATION_TIME_EXPIRED
            );
        }
    }

    private void checkIfReservationBelongsToUser(Long userId, Reservation reservation) {
        if (!isOwner(userId, reservation)) {
            throw new SaceForbiddenException(
                    ReservationExeceptionMessages.RESERVATION_NOT_BELONGS_TO_USER.formatted(reservation.getId(), userId)
            );
        }
    }

}