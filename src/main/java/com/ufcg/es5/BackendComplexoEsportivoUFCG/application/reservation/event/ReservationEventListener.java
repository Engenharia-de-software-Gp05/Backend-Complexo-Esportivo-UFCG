package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.event;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.event.DeletedEvent;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.service.MailService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationCancelledByAdminDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.SaceUser;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.court.CourtExceptionMessages;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.constants.sace_user.SaceUserExceptionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

import static com.ufcg.es5.BackendComplexoEsportivoUFCG.util.formatters.DateTimeUtils.DATE_FORMATTER;
import static com.ufcg.es5.BackendComplexoEsportivoUFCG.util.formatters.DateTimeUtils.TIME_FORMATTER;

@Component
public class ReservationEventListener {

    @Autowired
    private MailService mailService;

    @Autowired
    private CourtService courtService;

    @Autowired
    private SaceUserService saceUserService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleReservationDeletedEvent(DeletedEvent<ReservationCancelledByAdminDto> event) {
        ReservationCancelledByAdminDto savedEventDataDto = event.entity();
        SaceUser user = getUser(savedEventDataDto);
        String motive = savedEventDataDto.motive();
        String userName = user.getName();
        String userEmail = user.getEmail();
        String date = formatDate(savedEventDataDto.reservation().getStartDateTime());
        String hour = formatHour(savedEventDataDto.reservation().getStartDateTime(), savedEventDataDto.reservation().getEndDateTime());
        String courtName = getCourtName(savedEventDataDto);

        mailService.sendReservationCancelledByAdminEmail(userName, date, hour, courtName, motive, userEmail);
    }

    private SaceUser getUser(ReservationCancelledByAdminDto eventDataDto) {
        Long userId = eventDataDto.reservation().getId();
        return saceUserService.findById(userId)
                .orElseThrow(
                        () -> new SaceResourceNotFoundException(
                                SaceUserExceptionMessages.USER_WITH_ID_NOT_FOUND.formatted(userId)
                        )
                );
    }

    private String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }

    private String formatHour(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return startDateTime.format(TIME_FORMATTER) + " - " + endDateTime.format(TIME_FORMATTER);
    }

    private String getCourtName(ReservationCancelledByAdminDto eventDataDto) {
        Long courtId = eventDataDto.reservation().getId();
        return courtService.findById(courtId)
                .map(Court::getName)
                .orElseThrow(() -> new SaceResourceNotFoundException(
                        CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(courtId)));
    }
}
