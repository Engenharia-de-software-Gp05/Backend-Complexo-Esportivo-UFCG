package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.constants;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailTemplatePathConstants {

    private final String BASE_EMAIL_TEMPLATES_DIR;

    public MailTemplatePathConstants(@Value("${base.email.templates.dir}") String baseEmailTemplatesDir) {
        this.BASE_EMAIL_TEMPLATES_DIR = baseEmailTemplatesDir;
    }

    public String getSignUpConfirmationCodeEmailTemplatePath() {
        return BASE_EMAIL_TEMPLATES_DIR + "/sign_up_confirmation_code/sign-up-confirmation-code-email-minified.html";
    }

    public String getSignUpTemporaryPasswordEmailTemplatePath() {
        return BASE_EMAIL_TEMPLATES_DIR + "/sign_up_temporary_password/sign-up-temporary-password-email-minified.html";
    }

    public String getReservationConfirmedEmailTemplatePath() {
        return BASE_EMAIL_TEMPLATES_DIR + "/reservation_confirmed/reservation-confirmed-email-minified.html";
    }

    public String getReservationCancelledByUserTemplatePath() {
        return BASE_EMAIL_TEMPLATES_DIR + "/reservation_cancelled_by_user/reservation-cancelled-by-user-email-minified.html";
    }

    public String getReservationCancelledByAdminTemplatePath() {
        return BASE_EMAIL_TEMPLATES_DIR + "/reservation_cancelled_by_admin/reservation-cancelled-by-admin-email.minified.html";
    }

    public String getRecoverPasswordLinkTemplatePath() {
        return BASE_EMAIL_TEMPLATES_DIR + "/recover_password_link/recover-password-link-email-minified.html";
    }
}

