package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.auth.service.AuthService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.LoginResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.auth.RegisterRequestDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.UserResponseDto;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody
            @Valid
            LoginRequestDto data
    ){

        LoginResponseDto token = service.login(data);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
            @RequestBody
            @Valid
            RegisterRequestDto data
    ){
        UserResponseDto response = service.register(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
