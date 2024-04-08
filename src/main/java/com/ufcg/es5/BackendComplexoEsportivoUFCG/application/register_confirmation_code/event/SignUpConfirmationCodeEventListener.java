package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.register_confirmation_code.event;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.event.SavedEvent;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.service.MailService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.sace_user.service.SaceUserService;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sace_user.SaceUserNameEmailDto;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.sign_up_confirmation_code.SignUpConfirmationCodeUserIdConfirmationCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Component
public class SignUpConfirmationCodeEventListener {

    @Autowired
    private SaceUserService userService;

    @Autowired
    private MailService mailService;

    @EventListener
    public void handleSignUpConfirmationCodeSavedEvent(SavedEvent<SignUpConfirmationCodeUserIdConfirmationCodeDto> event) {
        SignUpConfirmationCodeUserIdConfirmationCodeDto confirmationCodeDto = event.entity();

        SaceUserNameEmailDto userNameEmailDto = userService.findNameEmailById(confirmationCodeDto.userId());

        mailService.sendSignUpConfirmationCodeEmail(userNameEmailDto.Name(), confirmationCodeDto.confirmationCode(), userNameEmailDto.Email());
    }
}
