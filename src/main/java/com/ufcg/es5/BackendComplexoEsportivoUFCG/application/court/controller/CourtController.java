package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.constants.CourtPathConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.global.PropertyConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Validated
@RestController
@RequestMapping(CourtPathConstants.PREFIX)
public class CourtController {

    @Autowired
    private CourtService service;

    @PostMapping(CourtPathConstants.CREATE_PATH)
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

    @PutMapping(CourtPathConstants.UPLOAD_IMAGE_PATH)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Successfully upload court image"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Failed uploading court image")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateImage(
            @RequestParam(PropertyConstants.ID)
            @NotNull Long id,
            @RequestPart(value = "courtImage") MultipartFile image
    ) {
        service.updateImageById(image, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CourtPathConstants.UPDATE_BY_ID_PATH)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updated court",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourtResponseDto.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Failed updated court",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourtResponseDto.class))})
    })
    public ResponseEntity<CourtResponseDto> updateById(
            @Valid
            @RequestBody
            CourtUpdateDto data,
            @NotNull
            @RequestParam(PropertyConstants.ID)
            Long id
    ) {
        CourtResponseDto response = service.updateById(data, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(CourtPathConstants.DELETE_BY_ID_PATH)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete a court.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204",
            description = "Court is deleted.")})
    public ResponseEntity<Void> deleteById(
            @NotNull
            @RequestParam(PropertyConstants.ID)
            Long id
    ) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(CourtPathConstants.FIND_BY_ID_PATH)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @Operation(summary = "Get court by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "returned court.",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CourtDetailedResponseDto.class)))})})
    public ResponseEntity<CourtDetailedResponseDto> findById(
            @NotNull
            @RequestParam(PropertyConstants.ID)
            Long id
    ) {
        CourtDetailedResponseDto response = service.findCourtDetailedResponseDtoById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(CourtPathConstants.FIND_ALL_PATH)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @Operation(summary = "Get all courts")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "returned court.",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CourtBasicResponseDto[].class)))})})
    public ResponseEntity<Collection<CourtBasicResponseDto>> findAll() {
        Collection<CourtBasicResponseDto> response = service.findAllCourtBasicResponseDto();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}