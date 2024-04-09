package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.job;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.register_confirmation_code.service.SignUpConfirmationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
@Profile("!test")
public class GarbageCollector {

    @Autowired
    SignUpConfirmationCodeService confirmationCodeService;

    /**
     * Collect garbage
     * <p>
     * Every 00:00
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void collect() {
        LocalDateTime dateTime = LocalDateTime.now();
        confirmationCodeService.collect(dateTime);
    }
}
