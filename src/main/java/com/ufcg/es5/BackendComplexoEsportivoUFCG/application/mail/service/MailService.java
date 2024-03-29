package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.message.Message;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendMail(Message message, String mailDestiny) throws MessagingException;

    void sendSignUpConfirmationCodeEmail(String name, String code, String mailDestiny);

    void sendSignUpFirstAccessLinkEmail(String name, String link, String mailDestiny);

    void sendReservationConfirmedEmail(String name, String date, String hour, String courtName, String mailDestiny);

    void sendReservationCancelledByUserEmail(String name, String date, String hour, String courtName, String mailDestiny);

    void sendReservationCancelledByAdminEmail(String name, String date, String hour, String courtName, String motive, String mailDestiny);

    void sendRecoverPasswordLinkEmail(String name, String link, String mailDestiny);
}
