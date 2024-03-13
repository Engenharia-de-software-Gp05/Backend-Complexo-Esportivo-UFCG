package com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {

    @Autowired
    private UserService userService;

    public Long getAuthenticatedUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return userService.findIdByEmail(userName).orElseThrow();
    }
}
