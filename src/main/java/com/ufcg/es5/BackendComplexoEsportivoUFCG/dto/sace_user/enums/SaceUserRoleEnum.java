package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums;

public enum SaceUserRoleEnum {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_PENDING("ROLE_PENDING");

    private final String role;

    SaceUserRoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
