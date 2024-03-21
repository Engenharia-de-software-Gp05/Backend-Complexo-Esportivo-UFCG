package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.enums;

public enum SaceUserAccountStatusEnum {
    VALID(true),
    PENDING(false),
    SUSPENDED(false);

    private final Boolean available;

    SaceUserAccountStatusEnum(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return this.available;
    }
}
