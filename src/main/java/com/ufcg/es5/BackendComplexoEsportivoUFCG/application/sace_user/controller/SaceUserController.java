package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserDataDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserNameEmailDto;
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
import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
public class SaceUserController {

    private static final String EMAIL_PROPERTY = "email";

    @Autowired
    private SaceUserService service;

    @GetMapping(value = "/by/email")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get user username and name by username.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "User username and name are returned.",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SaceUserNameEmailDto.class)))})})
    public ResponseEntity<SaceUserNameEmailDto> findByEmail(
            @Valid
            @NotNull
            @RequestParam(EMAIL_PROPERTY)
            String email
    ) {
        SaceUserNameEmailDto response = service.findByEmailAsDto(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/data")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get user username, name, studentId and phoneNumber")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "User email, name, studentId and phoneNumber returned.",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SaceUserDataDto.class)))})})
    public ResponseEntity<Collection<SaceUserDataDto>> findAllAsDto() {
        List<SaceUserDataDto> response = service.findAllAsDto();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/upload/profile/picture")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Successfully upload user profile picture."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Failed uploading user profile picture.")
    })
    public ResponseEntity<Void> uploadProfilePicture(
            @RequestPart(value = "profilePicture") MultipartFile picture
    ) {
        service.uploadProfilePicture(picture);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
