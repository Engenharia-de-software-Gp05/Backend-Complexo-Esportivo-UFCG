package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.messages;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.messages.basic_message.Message;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

public class Cancellation extends Message {

    public Cancellation(String name, Reservation reservation) {
        setTitle("CANCELAMENTO DE RESERVA - COMPLEXO UFCG");
        setMessage(messageCancellation(name, reservation));
    }

    public Cancellation(String name, Reservation reservation, String motivation) {
        setTitle("CANCELAMENTO DE RESERVA - COMPLEXO UFCG");
        setMessage(messageCancellation(name, reservation, motivation));
    }

    private String messageCancellation(String name, Reservation reservation) {
        return """
            <html>
            <body>
                <h2 style="color:#008080;">%s, %s</h2>
                <p style="color:#000000;">Sua reserva foi cancelada:</p>
                %s
            </body>
            </html>
            """.formatted(displayPeriodOfDay(), name.split(" ")[0], formatMessageReservation(reservation));
    }

    private String messageCancellation(String name, Reservation reservation, String motivation) {
        return """
            <html>
            <body>
                <h2 style="color:#008080;">%s, %s</h2>
                <p style="color:#000000;">Sua reserva foi cancelada:</p>
                %s
                <p style="color:#FF0000;">Motivação: %s</p>
            </body>
            </html>
            """.formatted(displayPeriodOfDay(), name.split(" ")[0], formatMessageReservation(reservation), motivation);
    }


}
