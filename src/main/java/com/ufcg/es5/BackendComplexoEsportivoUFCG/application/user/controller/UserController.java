package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.controller;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.service.UserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.UserResponseDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    private static final Log LOGGER = LogFactory.getLog(UserController.class);
    @GetMapping(value = "/by/email")
    public ResponseEntity<UserResponseDto> findByEmail(
            @RequestParam
            String email
    ){
        User user = service.findByEmail(email);
        LOGGER.info("buceta: " + user);
        UserResponseDto response = new UserResponseDto(user.getEmail(), user.getName());
        LOGGER.info("caralho: " + response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
