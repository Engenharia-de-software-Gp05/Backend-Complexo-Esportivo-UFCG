package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.messages;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.messages.basic_message.Message;

public class Register extends Message {

    public Register(String name, String codigo) {
        setTitle("CÓDIGO PARA CADASTRO - COMPLEXO UFCG");
        setMessage(messageRegister(name, codigo));
    }

    private String messageRegister(String name, String codigo) {
        return """
            <html>
            <body>
                <h2 style="color:#008080;">%s, Bem-vindo ao site do Complexo Esportivo da UFCG</h2>
                <p style="color:#000000;"> Olá, %s; Seu código de registro é: <strong>%s</strong></p>
            </body>
            </html>
            """.formatted(displayPeriodOfDay().toUpperCase(), name.split(" ")[0], codigo);
    }

}
