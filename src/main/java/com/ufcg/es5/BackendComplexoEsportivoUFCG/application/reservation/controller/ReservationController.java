package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@Validated
@RequestMapping("/reservation")
public class ReservationController {

    private static final String USER_ID_PROPERTY = "userId";
    @Autowired
    private ReservationService service;

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
    ) {
        Collection<ReservationResponseDto> response = service.findByUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/by/court")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get reservations by court and date time.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Court reservations are returned.",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReservationResponseDto[].class)))})})
    public ResponseEntity<Collection<ReservationResponseDto>> findByCourtAndDateTime(@RequestParam Long courtId, @RequestParam LocalDateTime date) {
        Collection<ReservationResponseDto> response = service.findByCourtAndDateTime(courtId, date);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/make-unavailable")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Make a reservation unavailable.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201",
            description = "Reservation time is made unavailable.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Reservation.class))})})
    public ResponseEntity<Reservation> makeUnavailable(
            @Valid
            @RequestParam
            ReservationSaveDto reservationSaveDto
    ) {
        return ResponseEntity.ok(service.makeUnavailable(reservationSaveDto));
    }


    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Create a reservation.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201",
            description = "Reservation is created.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Reservation.class))})})
    public ResponseEntity<Reservation> createReservation(
            @Valid
            @RequestParam
            ReservationSaveDto reservationSaveDto
    ) {
        return ResponseEntity.ok(service.createReservation(reservationSaveDto));
    }


    @DeleteMapping(value = "/delete")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete a reservation.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204",
            description = "Reservation is deleted.")})
    public void deleteReservation(
            @RequestParam("id")
            Long id
    ) {
        service.deleteReservation(id);
    }


}
