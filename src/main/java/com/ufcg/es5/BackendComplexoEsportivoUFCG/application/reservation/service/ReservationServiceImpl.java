package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository.ReservationRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.service.UserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.enums.UserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.User;
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
    private UserService userService;

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
        User user = userService.findById(userId).orElseThrow();

        Court court = courtService.findById(reservationSaveDto.courtId()).orElseThrow();

        Reservation reservation = makeReservation(reservationSaveDto, court, user);

        return repository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation makeUnavailable(ReservationSaveDto reservationMakeUnavailableDto) {
        Court court = courtService
                .findById(reservationMakeUnavailableDto.courtId())
                .orElseThrow();

        Reservation reservation = makeReservation(reservationMakeUnavailableDto, court, null);

        return repository.save(reservation);
    }


    @Override
    @Transactional
    public void deleteReservation(Long id) {
        Reservation reservation = repository.findById(id).orElseThrow();

        Long userId = authenticatedUser.getAuthenticatedUserId();
        if (isOwner(userId, reservation) || authenticatedUser.hasRole(UserRoleEnum.ROLE_ADMIN)) {
            repository.delete(reservation);
        } else {
            throw new RuntimeException("User has no permission to delete the reservation.");
        }
    }

    private boolean isOwner(Long userId, Reservation reservation) {
        return reservation.getUser().getId().equals(userId);
    }

    private Reservation makeReservation(ReservationSaveDto reservationSaveDto, Court court, User user) {
        return new Reservation(
                reservationSaveDto.startDateTime(),
                reservationSaveDto.endDateTime(),
                court,
                user
        );
    }

}