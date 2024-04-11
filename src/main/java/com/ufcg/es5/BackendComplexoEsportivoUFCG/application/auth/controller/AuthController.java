package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.constants.AuthPathConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service.AuthService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.global.PropertyConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.*;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserResponseDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(AuthPathConstants.PREFIX)
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping(AuthPathConstants.LOGIN_PATH)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully authentication",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthTokenDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "Failed authentication")})
    public ResponseEntity<AuthTokenDto> login(
            @Valid
            @RequestBody
            AuthUsernamePasswordDto data
    ) {
        AuthTokenDto token = service.login(data);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping(AuthPathConstants.REGISTER_PATH)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthTokenDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "Failed authentication")})
    public ResponseEntity<AuthTokenDto> register(
            @RequestBody
            @Valid
            AuthRegisterDataWithoutRolesDto data
    ) {
        AuthTokenDto response = service.register(data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(AuthPathConstants.REGISTER_ADMIN_PATH)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaceUserResponseDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "Failed authentication")})
    public ResponseEntity<SaceUserResponseDto> registerByAdmin(
            @RequestBody
            @Valid
            AuthRegisterDataWithRolesDto data
    ) {
        SaceUserResponseDto response = service.registerByAdmin(data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(AuthPathConstants.RECOVER_PASSWORD_PATH)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recover password email successfully sent")
    })
    public ResponseEntity<Void> recoverPassword(
            @NotBlank
            @RequestParam(PropertyConstants.USERNAME)
            String username
    ) {
        service.recoverPassword(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(AuthPathConstants.UPDATE_PASSWORD_PATH)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User password successfully changed."),
            @ApiResponse(
                    responseCode = "403",
                    description = "User password failed changed.")})
    public ResponseEntity<Void> updatePassword(
            @Valid
            AuthPasswordUpdateDto passwordUpdateDto
    ) {
        service.updatePassword(passwordUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(AuthPathConstants.CONFIRM_REGISTER_PATH)
    @PreAuthorize("hasRole('ROLE_PENDING')")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Register confirmed successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaceUserResponseDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "User password failed changed.",
                    content = {@Content(mediaType = "application/json")})})
    public ResponseEntity<Void> confirmRegisterCode(
            @NotBlank
            @Size(min = 6, max = 6)
            @RequestParam(PropertyConstants.CONFIRMATION_CODE)
            String confirmationCode
    ) {
        service.confirmEmailRegistered(confirmationCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
