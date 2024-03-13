package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.service.UserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.UserResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("user")
public class UserController {

    private static final String EMAIL_PROPERTY = "email";

    @Autowired
    private UserService service;

    @GetMapping(value = "/by/email")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get user username and name by username.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "User username and name are returned.",
            content = {@Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class)))})})
    public ResponseEntity<UserResponseDto> findByEmail(
            @Valid
            @NotNull
            @RequestParam(EMAIL_PROPERTY)
            String email
    ){
        User user = service.findByEmail(email);
        UserResponseDto response = new UserResponseDto(user.getEmail(), user.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
