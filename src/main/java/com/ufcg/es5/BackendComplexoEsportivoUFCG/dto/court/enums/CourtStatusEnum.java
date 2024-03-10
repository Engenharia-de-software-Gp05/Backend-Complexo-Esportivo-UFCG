package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums;

public enum CourtStatusEnum {
    AVAILABLE(true),
    UNAVAILABLE(false),
    PENDING(false);

    private final Boolean available;

    CourtStatusEnum(boolean available){
        this.available = available;
    }

    public boolean isAvailable(){
        return this.available;
    }
}
