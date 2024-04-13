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
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.constants.ReservationPathConstants;

@Validated
@RestController
@RequestMapping(ReservationPathConstants.PREFIX)
public class ReservationController {

    private static final String USER_ID_PROPERTY = "userId";

    @Autowired
    private ReservationService service;

    @GetMapping(ReservationPathConstants.BY_ID_PATH)
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

    @GetMapping(ReservationPathConstants.BY_COURT_PATH)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get reservations by court and date time.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Court reservations are returned.",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReservationResponseDto[].class)))})})
    public ResponseEntity<Collection<ReservationResponseDto>> findByCourtAndDateTime(
            @RequestParam Long courtId,
            @RequestParam LocalDateTime date) {
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
        Reservation response = service.makeUnavailable(reservationSaveDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping(ReservationPathConstants.CREATE_PATH)
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
        Reservation response = service.createReservation(reservationSaveDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(ReservationPathConstants.DELETE_BY_ID_PATH)
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete a reservation.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204",
            description = "Reservation is deleted.")})
    public ResponseEntity<Void> deleteById(
            @NotNull
            @RequestParam("id")
            Long id
    ) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(ReservationPathConstants.ADMIN_DELETE_BY_ID_PATH)
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete a reservation.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204",
            description = "Reservation is deleted.")})
    public ResponseEntity<Void> adminDeleteById(
            @NotNull
            @RequestParam("id")
            Long id
    ) {
        service.adminDeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
