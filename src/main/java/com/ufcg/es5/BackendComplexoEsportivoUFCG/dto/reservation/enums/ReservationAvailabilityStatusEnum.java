package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.enums;

public enum ReservationAvailabilityStatusEnum {
    UNAVAILABLE(false),
    BOOKED(false);

    private final Boolean available;

    ReservationAvailabilityStatusEnum(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return this.available;
    }
}
