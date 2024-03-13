package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

@RestController
@Validated
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService service;

    private static final String USER_ID_PROPERTY = "userId";

    @GetMapping(value = "/by/user-id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get reservations by user id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "User reservations are returned.",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReservationResponseDto[].class)))})})
    public ResponseEntity<Collection<ReservationResponseDto>> findByUserId(
            @Valid
            @RequestParam(USER_ID_PROPERTY)
            Long userId
    ){
        Collection<ReservationResponseDto> response = service.findByUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/by/court")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Collection<ReservationResponseDto>> findByCourtAndDateTime(@RequestParam Long courtId, @RequestParam LocalDateTime date) {
        Collection<ReservationResponseDto> response = service.findByCourtAndDateTime(courtId, date);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
