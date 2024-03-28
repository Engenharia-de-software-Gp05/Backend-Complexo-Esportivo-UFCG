package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service.AuthService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthRegisterDataWithRolesDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthRegisterDataWithoutRolesDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthTokenDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.AuthUsernamePasswordDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserResponseDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully authentication",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthTokenDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "Failed authentication",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthTokenDto.class))})
    })
    public ResponseEntity<AuthTokenDto> login(
            @Valid
            @RequestBody
            AuthUsernamePasswordDto data
    ) {
        AuthTokenDto token = service.login(data);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaceUserResponseDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "Failed authentication",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaceUserResponseDto.class))})})
    public ResponseEntity<SaceUserResponseDto> register(
            @RequestBody
            @Valid
            AuthRegisterDataWithoutRolesDto data
    ) {
        SaceUserResponseDto response = service.register(data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaceUserResponseDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "Failed authentication",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaceUserResponseDto.class))})})
    public ResponseEntity<SaceUserResponseDto> registerAddingRoles(
            @RequestBody
            @Valid
            AuthRegisterDataWithRolesDto data
    ) {
        SaceUserResponseDto response = service.registerWithRoles(data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/recover-password")
    @PreAuthorize("hasRole('ROLE_PENDING')")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recover password email successfully sent")
    })
    public ResponseEntity<Void> recoverPassword(
            @NotBlank
            String username
    ) {
        service.recoverPassword(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN') and hasRole('ROLE_ACTIVE')")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User password successfully changed.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaceUserResponseDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "User password failed changed.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaceUserResponseDto.class))})})
    public ResponseEntity<Void> updatePassword(
            @NotBlank
            String newPassword
    ) {
        service.updatePassword(newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
