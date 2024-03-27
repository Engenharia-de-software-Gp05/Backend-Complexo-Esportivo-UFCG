package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.messages.basic_message.Message;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendMail(Message message, String mailDestiny) throws MessagingException;

}
