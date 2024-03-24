package com.ufcg.es5.BackendComplexoEsportivoUFCG.util.security;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.HashSet;

public class SecurityContextUtils {

    public static void fakeAuthentication(Collection<String> roles) {
        fakeAuthentication("fakeUsername", new HashSet<>(roles));
    }

    public static void fakeAuthentication(String username, Collection<String> roles) {
        SaceUser user = new SaceUser(username, roles);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
