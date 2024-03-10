package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<Reservation>> findByCourtByDate(@RequestParam Long courtId, @RequestParam String date) {
        return ResponseEntity.ok(service.findByCourtByDate(courtId, date));
    }

    @GetMapping(value = "/by/user")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Reservation>> findByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(service.findByUserId(userId));
    }

    @DeleteMapping(value = "/delete")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public void deleteReservation(@RequestParam Long reservationId, Long userId) {
        service.deleteReservation(reservationId, userId);
    }
}
