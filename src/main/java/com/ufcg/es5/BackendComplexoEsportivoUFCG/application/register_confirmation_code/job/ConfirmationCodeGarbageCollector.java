package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.register_confirmation_code.job;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.register_confirmation_code.service.SignUpConfirmationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Profile("!test")
public class ConfirmationCodeGarbageCollector {

    @Autowired
    SignUpConfirmationCodeService confirmationCodeService;

    /**
     * Delete confirmation codes with more than 5 minutes
     * <p>
     * Every 1 minute at :00
     */
    @Scheduled(cron = "0 * * * * *")
    public void collect() {
        confirmationCodeService.collect();
    }
}
