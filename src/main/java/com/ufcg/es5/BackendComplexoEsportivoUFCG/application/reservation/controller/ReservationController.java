package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService service;

    @GetMapping(value = "/by/court")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Reservation>> findByCourtAndDateTime(@RequestParam Long courtId, @RequestParam LocalDateTime date) {
        return ResponseEntity.ok(service.findByCourtAndDateTime(courtId, date));
    }

    @GetMapping(value = "/by/user")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Reservation>> findByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(service.findByUserId(userId));
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Reservation> createReservation (
        @RequestParam Long userId, 
        @RequestParam Long courtId, 
        @RequestParam LocalDateTime start_date_time,
        @RequestParam LocalDateTime end_date_time
    ){
        return ResponseEntity.ok(service.createReservation(userId, courtId, start_date_time, end_date_time));
    }


    @DeleteMapping(value = "/delete")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public void deleteReservation(@RequestParam Long reservationId, Long userId) {
        service.deleteReservation(reservationId, userId);
    }

}
