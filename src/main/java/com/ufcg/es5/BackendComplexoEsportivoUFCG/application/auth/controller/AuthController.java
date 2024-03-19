package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.annotations.AuthorizationNotRequired;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service.AuthService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.RegisterRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.RegisterWithRolesRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.UserResponseDto;
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

    @AuthorizationNotRequired
    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully authentication",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "Failed authentication",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDto.class))})
    })
    public ResponseEntity<LoginResponseDto> login(
            @Valid
            @RequestBody
            LoginRequestDto data
    ) {
        LoginResponseDto token = service.login(data);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @AuthorizationNotRequired
    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "Failed authentication",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))})})
    public ResponseEntity<UserResponseDto> register(
            @RequestBody
            @Valid
            RegisterRequestDto data
    ) {
        UserResponseDto response = service.register(data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(
                    responseCode = "403",
                    description = "Failed authentication",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))})})
    public ResponseEntity<UserResponseDto> registerAddingRoles(
            @RequestBody
            @Valid
            RegisterWithRolesRequestDto data
    ) {
        UserResponseDto response = service.registerWithRoles(data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
