package com.ufcg.es5.BackendComplexoEsportivoUFCG.config.security;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceForbiddenException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.sace_user.SaceUserExceptionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthenticatedUser {

    @Autowired
    private SaceUserService saceUserService;

    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return saceUserService.findIdByEmail(userName).orElseThrow(
                () -> new SaceForbiddenException(
                        SaceUserExceptionMessages.NO_USER_AUTHENTICATED
                )
        );
    }

    public String getAuthenticatedUserUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public boolean hasRole(SaceUserRoleEnum role) {
        Long userId = getAuthenticatedUserId();
        Set<SaceUserRoleEnum> roles = saceUserService.findById(userId).get().getRoleEnums();
        return roles.contains(role);
    }
}
