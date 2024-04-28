package com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.projections.ReservationDetailedProjection;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.util.formatters.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.Collection;

public record ReservationDetailedDto(
        Long id,
        String courtName,
        Collection<String> courtImageUrl,
        String userName,
        @JsonFormat(pattern = DateTimeUtils.DATE_TIME_PATTERN)
        LocalDateTime startDateTime,
        @JsonFormat(pattern = DateTimeUtils.DATE_TIME_PATTERN)
        LocalDateTime endDateTime
) {
    public ReservationDetailedDto(ReservationDetailedProjection p) {
        this(p.getId(), p.getCourtName(), p.getCourtImageUrls(), p.getUserName(), p.getStartDateTime(), p.getEndDateTime());
    }
}
