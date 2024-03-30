package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtSaveDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.CourtUpdateDto;
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

@Validated
@RestController
@RequestMapping("/court")
public class CourtController {

    @Autowired
    private CourtService service;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully creating court",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourtResponseDto.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Failed creating court",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourtResponseDto.class))})
    })
    public ResponseEntity<CourtResponseDto> create(
            @Valid
            @RequestBody
            CourtSaveDto data
    ) {
        CourtResponseDto response = service.create(data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully update court",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourtResponseDto.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Failed update court",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourtResponseDto.class))})
    })
    public ResponseEntity<CourtResponseDto> update(
            @Valid
            @RequestBody
            CourtUpdateDto data,
            @NotNull
            @RequestParam("id")
            Long id
    ) {
        CourtResponseDto response = service.update(data, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}