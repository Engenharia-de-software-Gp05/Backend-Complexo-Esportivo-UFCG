package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.repository.ReservationRepository;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository repository;
    @Autowired 
    private UserRepository userRepository;

    @Override
    public JpaRepository<Reservation, Long> getRepository() {
        return repository;
    }

    @Override
    public List<Reservation> findByCourtByDate(Long courtId, String date) {
        return repository.findByCourtByDate(courtId, date);
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public void deleteReservation(Long reservationId, Long userId) {
        //TODO: verificar se o usuário tem a role de admin
        boolean isOwner = repository.findById(reservationId).get().getUser().getId().equals(userId);

        if(isOwner){
            repository.deleteById(reservationId);
        } 
        else{
            throw new RuntimeException("Usuário não tem permissão para deletar a reserva");
        }
    }


}