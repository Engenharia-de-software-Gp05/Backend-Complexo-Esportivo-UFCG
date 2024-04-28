package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.ReservationPathConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.global.PropertyConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.service.ReservationService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationDetailedDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.formatters.DateTimeUtils;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Validated
@RestController
@RequestMapping(ReservationPathConstants.PREFIX)
public class ReservationController {

    @Autowired
    private ReservationService service;

    @GetMapping(value = ReservationPathConstants.FIND_BY_COURT_ID_AND_USER_ID_PATH)
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
        Collection<ReservationResponseDto> response = service.findByCourtIdAndUserId(courtId, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = ReservationPathConstants.FIND_BY_COURT_ID_AND_DATE_RANGE_PATH)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Get reservations by court and date time.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Court reservations are returned.",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReservationResponseDto[].class)))})})
    public ResponseEntity<Collection<ReservationResponseDto>> findByCourtIdAndDateRange(
            @RequestParam(PropertyConstants.COURT_ID)
            @NotNull
            Long courtId,
            @RequestParam(PropertyConstants.START_DATE_TIME)
            @NotNull @DateTimeFormat(pattern = DateTimeUtils.DATE_TIME_PATTERN)
            LocalDateTime startDateTime,
            @RequestParam(PropertyConstants.END_DATE_TIME)
            @NotNull @DateTimeFormat(pattern = DateTimeUtils.DATE_TIME_PATTERN)
            LocalDateTime endDateTime
    ) {
        Collection<ReservationResponseDto> response = service.findByCourtIdAndDateRange(courtId, startDateTime, endDateTime);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(ReservationPathConstants.CREATE_PATH)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Create a reservation.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201",
            description = "Reservation is created.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReservationResponseDto.class))})})
    public ResponseEntity<ReservationResponseDto> create(
            @RequestBody
            @Valid
            ReservationSaveDto reservationSaveDto
    ) {
        ReservationResponseDto response = service.create(reservationSaveDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = ReservationPathConstants.DELETE_BY_ID_PATH)
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

    @DeleteMapping(value = ReservationPathConstants.DELETE_BY_ID_AND_MOTIVE_PATH)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "Delete a reservation and notify the reservation owner.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204",
            description = "Reservation is deleted.")})
    public ResponseEntity<Void> deleteByIdAndMotive(
            @NotNull
            @RequestParam(PropertyConstants.ID)
            Long id,
            @NotBlank
            @RequestParam(PropertyConstants.MOTIVE)
            String motive
    ) {
        service.deleteByIdAndMotive(id, motive);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = ReservationPathConstants.FIND_DETAILED_BY_AUTHENTICATED_USER_PATH)
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @Operation(summary = "Get reservations detailed from requester.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "User reservations are returned.",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReservationDetailedDto.class)))})})
    public ResponseEntity<Collection<ReservationDetailedDto>> findDetailedByAuthenticatedUser() {
        Collection<ReservationDetailedDto> response = service.findDetailedByAuthenticatedUser();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = ReservationPathConstants.FIND_ALL_DETAILED_PATH)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Get reservations detailed from all users.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "User reservations are returned.",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReservationDetailedDto.class)))})})
    public ResponseEntity<Collection<ReservationDetailedDto>> findAllDetailed() {
        Collection<ReservationDetailedDto> response = service.findAllDetailed();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
