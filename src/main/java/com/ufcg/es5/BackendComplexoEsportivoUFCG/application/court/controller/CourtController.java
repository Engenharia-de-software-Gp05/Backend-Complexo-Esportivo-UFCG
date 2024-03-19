package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtSaveDto;
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

import java.util.Collection;

@RestController
@Validated
@RequestMapping("/court")
public class CourtController {

    @Autowired
    CourtService service;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully creation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourtResponseDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "Failed creation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourtResponseDto.class))})
    })
    public ResponseEntity<CourtResponseDto> create(
            @Valid
            @RequestBody
            CourtSaveDto data
    ){
        CourtResponseDto response = service.create(data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}