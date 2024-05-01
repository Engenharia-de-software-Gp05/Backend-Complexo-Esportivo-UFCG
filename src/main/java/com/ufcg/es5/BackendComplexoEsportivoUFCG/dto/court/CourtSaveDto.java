package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.court.enums.CourtAvailabilityStatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.aspectj.bridge.IMessage;

import java.util.List;

public record CourtSaveDto(
        @NotBlank(message = "Court name cannot be empty")
        String name,

        @NotNull(message = "reservation duration cannot be null")
        Long reservationDuration,

        @NotNull(message = "minimum interval cannot be null")
        Long minimumIntervalBetweenReservation
) {
}
