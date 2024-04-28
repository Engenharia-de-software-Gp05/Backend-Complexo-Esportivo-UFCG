package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

public record ReservationCancelledByAdminDto(
        Reservation reservation,
        String motive
) {
}
