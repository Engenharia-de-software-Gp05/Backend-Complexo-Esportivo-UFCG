package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.global.PropertyConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationDetailedDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationSaveDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Validated
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService service;

    @GetMapping(value = "/by/court-id/user-id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get reservations by court id and user id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "User reservations are returned.",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReservationResponseDto[].class)))})})
    public ResponseEntity<Collection<ReservationResponseDto>> findByCourtIdUserId(
            @NotNull
            @RequestParam(PropertyConstants.COURT_ID)
            Long courtId,
            @NotNull
            @RequestParam(PropertyConstants.USER_ID)
            Long userId
    ) {
        Collection<ReservationResponseDto> response = service.findByCourtIdUserId(courtId, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/by/court")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get reservations by court and date time.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Court reservations are returned.",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReservationResponseDto[].class)))})})
    public ResponseEntity<Collection<ReservationResponseDto>> findByCourtIdAndDateRange(
            @RequestParam(PropertyConstants.COURT_ID)
            Long courtId,
            @RequestParam(PropertyConstants.START_DATE_TIME)
            LocalDateTime startDateTime,
            @RequestParam(PropertyConstants.END_DATE_TIME)
            LocalDateTime endDateTime) {
        Collection<ReservationResponseDto> response = service.findByCourtIdAndDateRange(courtId, startDateTime, endDateTime);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Create a reservation.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201",
            description = "Reservation is created.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReservationResponseDto.class))})})
    public ResponseEntity<ReservationResponseDto> create(
            @Valid
            @RequestBody
            ReservationSaveDto reservationSaveDto
    ) {
        ReservationResponseDto response = service.create(reservationSaveDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/by/id")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete a reservation.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204",
            description = "Reservation is deleted.")})
    public ResponseEntity<Void> deleteById(
            @NotNull
            @RequestParam(PropertyConstants.ID)
            Long id
    ) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/delete/by/id/motive")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Delete a reservation and notify the reservation owner.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204",
            description = "Reservation is deleted.")})
    public ResponseEntity<Void> deleteByIdAndMotive(
            @NotNull
            @RequestParam(PropertyConstants.ID)
            Long id,
            @NotBlank
            @RequestParam(PropertyConstants.ID)
            String motive
    ) {
        service.deleteByIdAndMotive(id, motive);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/by/id")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @Operation(summary = "Get reservations detailed from requester.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "User reservations are returned.",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReservationDetailedDto.class)))})})
    public ResponseEntity<ReservationDetailedDto> findDetailedById(
            @NotNull
            @RequestParam(PropertyConstants.ID)
            Long id
    ) {
        ReservationDetailedDto response = service.findDetailedById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
