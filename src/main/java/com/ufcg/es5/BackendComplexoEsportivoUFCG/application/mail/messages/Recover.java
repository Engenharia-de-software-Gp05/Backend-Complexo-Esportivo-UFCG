package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.messages;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.messages.basic_message.Message;

public class Recover extends Message {

    public Recover(String name, String link) {
        setTitle("RECUPERAÇÃO DE SENHA - COMPLEXO UFCG");
        setMessage(messageRecover(name, link));
    }

    private String messageRecover(String name, String link) {
        return """
            <html>
            <body>
                <h2 style="color:#008080;">%s, %s</h2>
                <p style="color:#000000;">Espero que você esteja tendo um %s!</p>
                <p style="color:#000000;">Para recuperar sua senha, clique no link abaixo:</p>
                <a href="%s">%s</a>
            </body>
            </html>
            """.formatted(displayPeriodOfDay(), name.split(" ")[0], link, link);
    }

}
