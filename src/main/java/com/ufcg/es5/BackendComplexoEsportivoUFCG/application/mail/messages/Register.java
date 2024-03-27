package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.messages;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.messages.basic_message.Message;

public class Register extends Message {
    private static final String REGISTER = "; O Complexo esportivo da UFCG Informa que o seu código de acesso é: ";

    public Register(String name, String codigo) {
        setTitle("CÓDIGO PARA CADASTRO - COMPLEXO UFCG");
        setMessage(messageRegister(name, codigo));
    }

    private String messageRegister(String name, String codigo) {
        return "<html>\n" +
                "<body>\n" +
                "    <h2 style=\"color:#008080;\">" + displayPeriodOfDay().toUpperCase() + ", Bem-vindo ao site do Complexo Esportivo da UFCG</h2>\n" +
                "    <p style=\"color:#000000;\"> Olá, " + name.split(" ")[0] + "; Seu código de registro é: <strong>" + codigo + "</strong></p>\n" +
                "</body>\n" +
                "</html>";
    }

}
