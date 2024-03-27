package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.messages.basic_message;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

import java.time.LocalTime;
import java.util.Random;

public class Message {

    private String title;

    private String message;

    public static String displayPeriodOfDay() {
        LocalTime now = LocalTime.now();

        LocalTime morning = LocalTime.of(12, 0);
        LocalTime afternoon = LocalTime.of(18, 0);

        if (now.isBefore(morning)) {
            return "Bom dia";
        } else if (now.isBefore(afternoon)) {
            return "Boa tarde";
        } else {
            return "Boa noite";
        }
    }

    public static String formatMessageReservation(Reservation reservation) {
        return "Nome da quadra: " + reservation.getCourt().getName() + "/n" +
                "Previsão de início às: " + reservation.getStartDateTime() + "/n" +
                "Previsão de fim às: " + reservation.getEndDateTime();
    }

    public static String generateRandomString() {
        int length = 6;
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();
        int randomIndex;

        for (int i = 0; i < length; i++) {
            int flag = random.nextInt(2);

            if (flag == 1) {
                randomIndex = random.nextInt(10);
                codigo.append((char) ('0' + randomIndex));
            } else {
                randomIndex = random.nextInt(26);
                randomIndex += (random.nextBoolean() ? 'A' : 'a');
                codigo.append((char) randomIndex);
            }
        }

        return codigo.toString();
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

