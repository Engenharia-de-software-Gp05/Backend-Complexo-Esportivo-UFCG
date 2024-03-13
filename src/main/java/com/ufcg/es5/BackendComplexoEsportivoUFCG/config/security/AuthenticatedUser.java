package com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.user.service.UserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.enums.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthenticatedUser {

    @Autowired
    private UserService userService;

    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return userService.findIdByEmail(userName).orElseThrow();
    }

    public boolean hasRole(UserRoleEnum role) {
        Long userId = getAuthenticatedUserId();
        Set<UserRoleEnum> roles = userService.findById(userId).get().getRoleEnums();
        return roles.contains(role);
    }
}
