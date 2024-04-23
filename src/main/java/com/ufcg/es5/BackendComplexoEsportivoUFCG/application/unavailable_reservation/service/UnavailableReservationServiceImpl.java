package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.unavailable_reservation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository.ReservationRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.unavailable_reservation.repository.UnavailableReservationRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.unavailable_reservation.UnavailableReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.UnavailableReservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.UnavailableReservationResponseProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.court.CourtExceptionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class UnavailableReservationServiceImpl implements UnavailableReservationService {

    @Autowired
    private UnavailableReservationRepository repository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CourtService courtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpaRepository<UnavailableReservation, Long> getRepository() {
        return repository;
    }

    @Override
    public Collection<UnavailableReservationResponseDto> findByCourtIdAndDateRange(Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Collection<UnavailableReservationResponseProjection> projections = repository.findByCourtIdAndDateRange(courtId, startDateTime, endDateTime);
        return projections.stream().map(UnavailableReservationResponseDto::new).toList();
    }

    @Override
    public Optional<UnavailableReservation> findByCourtIdAndStartDateTime(Long courtId, LocalDateTime startDateTime) {
        return repository.findByCourtIdAndStartDateTime(courtId, startDateTime);
    }

    @Override
    public boolean existsByCourtIdAndStartDateTime(Long courtId, LocalDateTime startDateTime) {
        return findByCourtIdAndStartDateTime(courtId, startDateTime).isPresent();
    }

    @Override
    @Transactional
    public UnavailableReservationResponseDto create(ReservationSaveDto reservationSaveDto) {
        Reservation existingReservation = findExistingReservation(reservationSaveDto);
        Court court = findCourt(reservationSaveDto);
        LocalDateTime startDateTime = reservationSaveDto.startDateTime();
        LocalDateTime endDateTime = calculateEndDateTime(startDateTime, court.getReservationDuration());

        if (existingReservation != null) {
            cancelExistingReservation(existingReservation, reservationSaveDto.unavailabilityReason());
        }

        UnavailableReservation unavailableReservation = saveUnavailableReservation(startDateTime, endDateTime, court);
        return objectMapper.convertValue(unavailableReservation, UnavailableReservationResponseDto.class);
    }

    private Reservation findExistingReservation(ReservationSaveDto reservationSaveDto) {
        return reservationService.findByCourtIdAndStartDateTime(
                reservationSaveDto.courtId(), reservationSaveDto.startDateTime()).orElse(null);
    }

    private Court findCourt(ReservationSaveDto reservationSaveDto) {
        Long courtId = reservationSaveDto.courtId();
        return courtService.findById(courtId).orElseThrow(() ->
                new SaceResourceNotFoundException(
                        CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(courtId)
                )
        );
    }

    private LocalDateTime calculateEndDateTime(LocalDateTime startDateTime, Long durationInMinutes) {
        return startDateTime.plusMinutes(durationInMinutes);
    }

    private void cancelExistingReservation(Reservation existingReservation, String unavailabilityReason) {
        reservationService.deleteByIdAndMotive(existingReservation.getId(), unavailabilityReason);
    }

    private UnavailableReservation saveUnavailableReservation(LocalDateTime startDateTime, LocalDateTime endDateTime, Court court) {
        UnavailableReservation unavailableReservation = new UnavailableReservation(startDateTime, endDateTime, court);
        return repository.save(unavailableReservation);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.deleteById(id);
    }
}
