package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums;

public enum CourtAvailabilityStatusEnum {
    AVAILABLE(true),
    UNAVAILABLE(false);

    private final Boolean available;

    CourtAvailabilityStatusEnum(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return this.available;
    }
}
