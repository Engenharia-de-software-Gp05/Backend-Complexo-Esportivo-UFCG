package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                    array = @ArraySchema(schema = @Schema(implementation = SaceUserResponseDto.class)))})})
    public ResponseEntity<SaceUserResponseDto> findByEmail(
            @Valid
            @NotNull
            @RequestParam(EMAIL_PROPERTY)
            String email
    ) {
        SaceUser user = service.findByEmail(email);
        SaceUserResponseDto response = new SaceUserResponseDto(user.getEmail(), user.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}