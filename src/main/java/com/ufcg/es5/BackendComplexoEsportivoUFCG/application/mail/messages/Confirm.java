package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.messages;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.messages.basic_message.Message;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.Reservation;

public class Confirm extends Message {

    public Confirm(String name, Reservation reservation) {
        setTitle("COMFIRMAÇÃO DE RESERVA - COMPLEXO UFCG");
        setMessage(messageConfirmation(name, reservation));
    }

    private String messageConfirmation(String name, Reservation reservation) {
        return """
            <html>
            <body>
                <h2 style="color:#008080;">%s, %s</h2>
                <p style="color:#000000;">Sua reserva foi confirmada:</p>
                %s
            </body>
            </html>
            """.formatted(displayPeriodOfDay(), name.split(" ")[0], formatMessageReservation(reservation));
    }


}
