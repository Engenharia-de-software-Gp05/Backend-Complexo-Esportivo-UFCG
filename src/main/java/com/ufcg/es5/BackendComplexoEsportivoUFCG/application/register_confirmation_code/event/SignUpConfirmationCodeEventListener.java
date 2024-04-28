package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.register_confirmation_code.event;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.event.SavedEvent;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.service.MailService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserNameEmailDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sign_up_confirmation_code.SignUpConfirmationCodeSavedEventDataDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sign_up_confirmation_code.SignUpConfirmationCodeUserIdConfirmationCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class SignUpConfirmationCodeEventListener {

    @Autowired
    private MailService mailService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleSignUpConfirmationCodeSavedEvent(SavedEvent<SignUpConfirmationCodeSavedEventDataDto> event) {
        SignUpConfirmationCodeSavedEventDataDto savedEventDataDto = event.entity();

        mailService.sendSignUpConfirmationCodeEmail(savedEventDataDto.name(), savedEventDataDto.confirmationCode(), savedEventDataDto.email());
    }
}
