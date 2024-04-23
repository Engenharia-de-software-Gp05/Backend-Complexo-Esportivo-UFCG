package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.reservation.event;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.court.service.CourtService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.event.DeletedEvent;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.event.SavedEvent;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.service.MailService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationCancelledByAdminDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationCancelledByUserDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.reservation.ReservationSavedDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Court;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;
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
    public void handleReservationDeletedByAdminEvent(DeletedEvent<ReservationCancelledByAdminDto> event) {
        ReservationCancelledByAdminDto cancelledByAdminDto = event.entity();
        handleCancellationEvent(cancelledByAdminDto.reservation(), "Admin", cancelledByAdminDto.motive());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleReservationDeletedByUserEvent(DeletedEvent<ReservationCancelledByUserDto> event) {
        ReservationCancelledByUserDto cancelledByUserDto = event.entity();
        handleCancellationEvent(cancelledByUserDto.reservation(), "User", null);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleReservationSavedEvent(SavedEvent<ReservationSavedDto> event) {
        ReservationSavedDto savedEventDto = event.entity();
        Reservation reservationDto = savedEventDto.reservation();
        if (reservationDto.getSaceUser() != null) {
            sendConfirmationEmail(reservationDto);
        }
    }

    private void handleCancellationEvent(Reservation reservation, String cancelledBy, String motive) {
        SaceUser user = getUserById(reservation.getSaceUser().getId());
        Court court = getCourtById(reservation.getCourt().getId());

        String name = user.getName();
        String date = formatDate(reservation.getStartDateTime());
        String hour = formatHour(reservation.getStartDateTime(), reservation.getEndDateTime());
        String courtName = court.getName();
        String userEmail = user.getEmail();

        if (cancelledBy.equals("Admin")) {
            mailService.sendReservationCancelledByAdminEmail(name, date, hour, courtName, motive, userEmail);
        } else {
            mailService.sendReservationCancelledByUserEmail(name, date, hour, courtName, userEmail);
        }
    }

    private void sendConfirmationEmail(Reservation reservation) {
        SaceUser user = getUserById(reservation.getSaceUser().getId());
        Court court = getCourtById(reservation.getCourt().getId());

        String name = user.getName();
        String date = formatDate(reservation.getStartDateTime());
        String hour = formatHour(reservation.getStartDateTime(), reservation.getEndDateTime());
        String courtName = court.getName();
        String userEmail = user.getEmail();

        mailService.sendReservationConfirmedEmail(name, date, hour, courtName, userEmail);
    }

    private SaceUser getUserById(Long userId) {
        return saceUserService.findById(userId)
                .orElseThrow(() -> new SaceResourceNotFoundException(
                        SaceUserExceptionMessages.USER_WITH_ID_NOT_FOUND.formatted(userId)
                ));
    }

    private Court getCourtById(Long courtId) {
        return courtService.findById(courtId)
                .orElseThrow(() -> new SaceResourceNotFoundException(
                        CourtExceptionMessages.COURT_WITH_ID_NOT_FOUND.formatted(courtId)
                ));
    }

    private String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }

    private String formatHour(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return startDateTime.format(TIME_FORMATTER) + " - " + endDateTime.format(TIME_FORMATTER);
    }
}