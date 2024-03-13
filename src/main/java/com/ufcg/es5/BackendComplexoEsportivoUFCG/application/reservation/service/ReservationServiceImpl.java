package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.repository.CourtRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository.ReservationRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.service.UserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security.AuthenticatedUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.enums.UserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.User;
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
    private CourtRepository courtRepository;

    @Autowired
    private AuthenticatedUser authenticatedUser;

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
    public Reservation createReservation(Long userId, Long courtId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        // TODO : teste de nulidade
        Court court = courtRepository.findById(courtId).get();
        User user = userService.findById(userId).get();

        Reservation reservation = new Reservation(startDateTime, endDateTime, court, user);
        return repository.save(reservation);
    }


    @Override
    public void deleteReservation(Long id) {
        //TODO: verificar se o usuário tem a role de admin
        Reservation reservation = repository.findById(id).orElseThrow();

        Long userId = authenticatedUser.getAuthenticatedUserId();
        if (isOwner(userId, reservation) || authenticatedUser.hasRole(UserRoleEnum.ROLE_ADMIN)) {
            repository.delete(reservation);
        } else {
            throw new RuntimeException("Usuário não tem permissão para deletar a reserva");
        }
    }

    private boolean isOwner(Long userId, Reservation reservation) {
        return reservation.getUser().getId().equals(userId);
    }

}