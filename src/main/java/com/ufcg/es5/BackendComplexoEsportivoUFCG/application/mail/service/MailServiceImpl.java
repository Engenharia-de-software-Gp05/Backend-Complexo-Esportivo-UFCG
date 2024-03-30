package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.service;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.constants.MailSubjectConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.constants.MailTemplatePathConstants;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.message.Message;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.InternalException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    private static final String NAME = "name";
    private static final String DATE = "date";
    private static final String HOUR = "hour";
    private static final String COURT_NAME = "courtName";
    private static final String MOTIVE = "motive";
    private static final String CODE = "code";
    private static final String LINK = "link";
    private static final String TEMPORARY_PASSWORD = "temporaryPassword";

    @Autowired
    private final JavaMailSender javaMailSender;

    private final MustacheFactory mf = new DefaultMustacheFactory();

    public MailServiceImpl(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Transactional
    public void sendMail(Message message, String mailDestiny) throws InternalException {
        try {
            var mail = javaMailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mail, true, "UTF-8");

            helper.setTo(mailDestiny);
            helper.setSubject(message.getTitle());
            helper.setText(message.getMessage(), true);

            javaMailSender.send(mail);
        } catch (MessagingException e) {
            throw new InternalException("Error sending mail" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void sendSignUpConfirmationCodeEmail(String name, String code, String mailDestiny) {
        Map<String, Object> dataToInject = new HashMap<>();

        dataToInject.put(NAME, name);
        dataToInject.put(CODE, code);

        Message message = makeMessage(
                MailSubjectConstants.SIGN_UP_CONFIRMATION_CODE_EMAIL_SUBJECT,
                MailTemplatePathConstants.SIGN_UP_CONFIRMATION_CODE_EMAIL_TEMPLATE_PATH,
                dataToInject
        );

        sendMail(message, mailDestiny);
    }

    @Override
    public void sendSignUpTemporaryPasswordEmail(String name, String temporaryPassword, String mailDestiny) {
        Map<String, Object> dataToInject = new HashMap<>();

        dataToInject.put(NAME, name);
        dataToInject.put(TEMPORARY_PASSWORD, temporaryPassword);

        Message message = makeMessage(
                MailSubjectConstants.SIGN_UP_TEMPORARY_PASSWORD_EMAIL_SUBJECT,
                MailTemplatePathConstants.SIGN_UP_TEMPORARY_PASSWORD_EMAIL_TEMPLATE_PATH,
                dataToInject
        );

        sendMail(message, mailDestiny);
    }

    @Override
    public void sendReservationConfirmedEmail(String name, String date, String hour, String courtName, String mailDestiny) {
        Map<String, Object> dataToInject = new HashMap<>();

        dataToInject.put(NAME, name);
        dataToInject.put(DATE, date);
        dataToInject.put(HOUR, hour);
        dataToInject.put(COURT_NAME, courtName);

        Message message = makeMessage(
                MailSubjectConstants.RESERVATION_CONFIRMED_EMAIL_SUBJECT,
                MailTemplatePathConstants.RESERVATION_CONFIRMED_EMAIL_TEMPLATE_PATH,
                dataToInject
        );

        sendMail(message, mailDestiny);
    }

    @Override
    public void sendReservationCancelledByUserEmail(String name, String date, String hour, String courtName, String mailDestiny) {
        Map<String, Object> dataToInject = new HashMap<>();

        dataToInject.put(NAME, name);
        dataToInject.put(DATE, date);
        dataToInject.put(HOUR, hour);
        dataToInject.put(COURT_NAME, courtName);

        Message message = makeMessage(
                MailSubjectConstants.RESERVATION_CANCELLED_BY_USER_SUBJECT,
                MailTemplatePathConstants.RESERVATION_CANCELLED_BY_USER_TEMPLATE_PATH,
                dataToInject
        );

        sendMail(message, mailDestiny);
    }

    @Override
    public void sendReservationCancelledByAdminEmail(String name, String date, String hour, String courtName, String motive, String mailDestiny) {
        Map<String, Object> dataToInject = new HashMap<>();

        dataToInject.put(NAME, name);
        dataToInject.put(DATE, date);
        dataToInject.put(HOUR, hour);
        dataToInject.put(COURT_NAME, courtName);
        dataToInject.put(MOTIVE, motive);


        Message message = makeMessage(
                MailSubjectConstants.RESERVATION_CANCELLED_BY_ADMIN_SUBJECT,
                MailTemplatePathConstants.RESERVATION_CANCELLED_BY_ADMIN_TEMPLATE_PATH,
                dataToInject
        );

        sendMail(message, mailDestiny);
    }

    @Override
    public void sendRecoverPasswordLinkEmail(String name, String link, String mailDestiny) {
        Map<String, Object> dataToInject = new HashMap<>();

        dataToInject.put(NAME, name);
        dataToInject.put(LINK, link);

        Message message = makeMessage(
                MailSubjectConstants.RECOVER_PASSWORD_LINK_SUBJECT,
                MailTemplatePathConstants.RECOVER_PASSWORD_LINK_TEMPLATE_PATH,
                dataToInject
        );

        sendMail(message, mailDestiny);
    }


    private Message makeMessage(String subject, String templatePath, Map<String, Object> dataToInject){
        Mustache mustache = mf.compile(templatePath);

        StringWriter writer = new StringWriter();

        mustache.execute(writer, dataToInject);
        String template = writer.toString();

        return new Message(subject, template);
    }
}
