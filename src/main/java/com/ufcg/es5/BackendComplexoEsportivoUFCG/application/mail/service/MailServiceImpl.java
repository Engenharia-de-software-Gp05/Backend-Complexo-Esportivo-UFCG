package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.service;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.messages.basic_message.Message;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private final JavaMailSender javaMailSender;

    public MailServiceImpl(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(Message message, String mailDestiny) throws MessagingException {
        var mail = javaMailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mail, true, "UTF-8");

        helper.setTo(mailDestiny);
        helper.setSubject(message.getTitle());
        helper.setText(message.getMessage(), true);

        helper.addAttachment(
                "Sherek.jpg",
                new ClassPathResource("file/Sherek.jpg")
        );

        javaMailSender.send(mail);
    }
}
