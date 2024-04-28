package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.event.DeletedEvent;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.event.SavedEvent;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository.ReservationRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.unavailable_reservation.service.UnavailableReservationService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.*;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.ReservationDetailedProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.ReservationResponseProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceConflictException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceForbiddenException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceInvalidArgumentException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.court.CourtExceptionMessages;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.reservation.ReservationExeceptionMessages;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.sace_user.SaceUserExceptionMessages;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final int CANCELLATION_TIME_LIMIT = 24;

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private SaceUserService saceUserService;

    @Autowired
    @Lazy
    private UnavailableReservationService unavailableReservationService;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private CourtService courtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public JpaRepository<Reservation, Long> getRepository() {
        return repository;
    }

    @Override
    public Collection<ReservationResponseDto> findByCourtIdAndDateRange(Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Collection<ReservationResponseProjection> projections = repository.findByCourtIdAndDateRange(courtId, startDateTime, endDateTime);
        return projections.stream().map(ReservationResponseDto::new).toList();
    }

    @Override
    @Transactional
    public ReservationResponseDto create(ReservationSaveDto reservationSaveDto) {
        SaceUser user = getAuthenticatedUser();
        Court court = getCourtById(reservationSaveDto.courtId());
        LocalDateTime startDateTime = reservationSaveDto.startDateTime();
        LocalDateTime endDateTime = calculateEndDateTime(startDateTime, court);

        validateReservation(court.getId(), user.getId(), startDateTime);

        Reservation reservation = makeReservation(startDateTime, endDateTime, court, user);

        reservation = save(reservation);
        publishSavedEvent(reservation);

        return objectMapper.convertValue(reservation, ReservationResponseDto.class);
    }

    private void validateReservation(Long courtId, Long userId, LocalDateTime startDateTime) {
        Court court = getCourtById(courtId);

        LocalDateTime endDateTime = calculateEndDateTime(startDateTime, court);

        checkReservationAvailability(courtId, startDateTime);
        checkTimeAvailability(courtId, startDateTime, endDateTime);
        checkUserReservationLimit(court, userId, startDateTime);
    }

    private void publishSavedEvent(Reservation reservation) {
        eventPublisher.publishEvent(new SavedEvent<>(new ReservationSavedDto(reservation), ReservationSavedDto.class));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Reservation reservation = getReservation(id);
        Long userId = authenticatedUser.getAuthenticatedUserId();

        validateReservationOwnership(userId, reservation);
        validateCancellationTimeLimit(reservation);

        publishCancellationEventByUser(reservation);

        repository.delete(reservation);
    }

    private void publishCancellationEventByUser(Reservation reservation) {
        eventPublisher.publishEvent(new DeletedEvent<>(new ReservationCancelledByUserDto(reservation), ReservationCancelledByUserDto.class));
    }

    @Override
    @Transactional
    public void deleteByIdAndMotive(Long id, String motive) {
        Reservation reservation = getReservation(id);

        publishCancellationEventByAdmin(reservation, motive);
        repository.delete(reservation);
    }

    private void publishCancellationEventByAdmin(Reservation reservation, String motive) {
        eventPublisher.publishEvent(new DeletedEvent<>(new ReservationCancelledByAdminDto(reservation, motive), ReservationCancelledByAdminDto.class));
    }

    @Override
    public Collection<ReservationResponseDto> findByCourtIdAndUserId(Long courtId, Long userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();

        Collection<ReservationResponseProjection> projections = repository.findByCourtIdUserIdAndDateTime(courtId, userId, startOfDay);
        return projections.stream().map(ReservationResponseDto::new).toList();
    }

    private static LocalDateTime calculateEndDateTime(LocalDateTime startDateTime, Court court) {
        return startDateTime.plusMinutes(court.getReservationDuration());
    }

    @Override
    public Boolean existsByCourtIdAndTimeInterval(Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return !findByCourtIdAndTimeInterval(courtId, startDateTime, endDateTime).isEmpty();
    }

    @Override
    public Collection<ReservationResponseDto> findByCourtIdAndTimeInterval(Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Collection<ReservationResponseProjection> projections = repository.findByCourtIdAndTimeInterval(courtId, startDateTime, endDateTime);
        return projections.stream().map(ReservationResponseDto::new).toList();
    }

    @Override
    public Boolean existsByUserIdAndStartDateTime(Long userId, LocalDateTime startDateTime) {
        return !findByUserIdAndStartDateTime(userId, startDateTime).isEmpty();
    }

    @Override
    public Collection<ReservationResponseDto> findByUserIdAndStartDateTime(Long userId, LocalDateTime startDateTime) {
        Collection<ReservationResponseProjection> projections = repository.findByUserIdAndStartDateTime(userId, startDateTime);
        return projections.stream().map(ReservationResponseDto::new).toList();
    }

    @Override
    public Boolean existsByCourtIdUserIdAndTimeInterval(Long courtId, Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return !findByCourtIdUserIdAndTimeInterval(courtId, userId, startDateTime, endDateTime).isEmpty();
    }

    @Override
    public Collection<ReservationResponseDto> findByCourtIdUserIdAndTimeInterval(Long courtId, Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Collection<ReservationResponseProjection> projections = repository.findByCourtIdUserIdAndTimeInterval(courtId, userId, startDateTime, endDateTime);
        return projections.stream().map(ReservationResponseDto::new).toList();
    }

    @Override
    public Optional<Reservation> findByCourtIdAndStartDateTime(Long courtId, LocalDateTime startDateTime) {
        return Optional.empty();
    }

    @Override
    public Collection<ReservationDetailedDto> findDetailedByAuthenticatedUser() {
        Long userId = authenticatedUser.getAuthenticatedUserId();
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();

        Collection<ReservationDetailedProjection> projection = repository.findDetailedByUserIdAndDateTime(userId, startOfDay);

        return projection.stream().map(ReservationDetailedDto::new).toList();
    }

    @Override
    public Collection<ReservationDetailedDto> findAllDetailed() {
        Collection<ReservationDetailedProjection> projection = repository.findAllDetailed();

        return projection.stream().map(ReservationDetailedDto::new).toList();
    }

    private SaceUser getAuthenticatedUser() {
        Long userId = authenticatedUser.getAuthenticatedUserId();
        return saceUserService.findById(userId).orElseThrow(
                () -> new SaceResourceNotFoundException(
                        SaceUserExceptionMessages.USER_WITH_ID_NOT_FOUND.formatted(userId)
                )
        );
    }

    private boolean isOwner(Long userId, Reservation reservation) {
        return reservation.getSaceUser().getId().equals(userId);
    }

    private Reservation getReservation(Long id) {
        return this.findById(id).orElseThrow(() -> new SaceResourceNotFoundException(
                ReservationExeceptionMessages.RESERVATION_WITH_ID_NOT_FOUND.formatted(id)
        ));
    }

    private void validateReservationOwnership(Long userId, Reservation reservation) {
        if (!isOwner(userId, reservation)) {
            throw new SaceForbiddenException(
                    ReservationExeceptionMessages.RESERVATION_WITH_ID_NOT_BELONGS_TO_USER_WITH_ID.formatted(reservation.getId(), userId)
            );
        }
    }

    private void validateCancellationTimeLimit(Reservation reservation) {
        LocalDateTime now = LocalDateTime.now();

        if (reservation.getStartDateTime().isBefore(now.plusHours(CANCELLATION_TIME_LIMIT))) {
            throw new SaceForbiddenException(
                    ReservationExeceptionMessages.RESERVATION_CANCELLATION_TIME_EXPIRED
            );
        }
    }

    private void checkReservationAvailability(Long courtId, LocalDateTime startDateTime) {
        if (unavailableReservationService.existsByCourtIdAndStartDateTime(courtId, startDateTime)) {
            throw new SaceInvalidArgumentException(
                    ReservationExeceptionMessages.UNAVAILABILITY_FOUND_FOR_THE_GIVEN_TIME
            );
        }
    }

    private void checkUserReservationLimit(Court court, Long userId, LocalDateTime startDateTime) {
        Long minimumTimeBetweenReservation = court.getMinimumIntervalBetweenReservation();
        LocalDateTime earliestAllowedReservationTime = startDateTime.minusDays(minimumTimeBetweenReservation);
        LocalDateTime latestAllowedReservationTime = startDateTime.plusDays(minimumTimeBetweenReservation);

        validateIfUserExceededReservationLimit(court, userId, earliestAllowedReservationTime, latestAllowedReservationTime);
        validateIfUserHasReservationInOtherCourtForSameStartDateTime(userId, startDateTime);
    }

    private void validateIfUserHasReservationInOtherCourtForSameStartDateTime(Long userId, LocalDateTime startDateTime) {
        if (existsByUserIdAndStartDateTime(userId, startDateTime)) {
            throw new SaceInvalidArgumentException(
                    ReservationExeceptionMessages.USER_WITH_ID_ALREADY_HAS_A_RESERVATION_FOR_START_DATE_TIME.formatted(userId, startDateTime)
            );
        }
    }

    private void validateIfUserExceededReservationLimit(Court court, Long userId, LocalDateTime earliestAllowedReservationTime, LocalDateTime latestAllowedReservationTime) {
        if (existsByCourtIdUserIdAndTimeInterval(court.getId(), userId, earliestAllowedReservationTime, latestAllowedReservationTime)) {
            throw new SaceInvalidArgumentException(
                    ReservationExeceptionMessages.RESERVATION_LIMIT_EXCEEDED_FOR_INTERVAL_TIME_BETWEEN_RESERVATIONS
            );
        }
    }

    private void checkTimeAvailability(Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (existsByCourtIdAndTimeInterval(courtId, startDateTime, endDateTime)) {
            throw new SaceConflictException(ReservationExeceptionMessages.RESERVATION_TIME_CONFLICT
                    .formatted(startDateTime, endDateTime));
        }
    }

    private Court getCourtById(Long courtId) {
        return courtService.findById(courtId)
                .orElseThrow(() -> new SaceResourceNotFoundException(
                                CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(courtId)
                        )
                );
    }

    private Reservation makeReservation(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            Court court,
            SaceUser user
    ) {
        return new Reservation(
                startDateTime,
                endDateTime,
                court,
                user
        );
    }
}