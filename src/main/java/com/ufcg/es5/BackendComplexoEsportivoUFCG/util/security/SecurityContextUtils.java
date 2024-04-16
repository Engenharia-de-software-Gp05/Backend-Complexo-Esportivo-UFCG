package com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums.SaceUserRoleEnum;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityContextUtils {

    public static void fakeAuthentication(Collection<String> roles) {
        Set<SaceUserRoleEnum> roleEnums = roles.stream()
                .map(SaceUserRoleEnum::valueOf)
                .collect(Collectors.toSet());
        fakeAuthentication("fakeUsername", roleEnums);
    }


    public static void fakeAuthentication(String username, Set<SaceUserRoleEnum> roles) {
        SaceUser user = new SaceUser(username, roles);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
