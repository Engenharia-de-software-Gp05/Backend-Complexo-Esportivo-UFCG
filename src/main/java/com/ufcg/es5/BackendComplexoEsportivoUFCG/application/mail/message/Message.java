package com.ufcg.es5.BackendComplexoEsportivoUFCG.application.mail.message;

public class Message {

    private String title;
    private String message;

    public Message(String title, String message){
        this.title = title;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title;}
    public void setMessage(String message) {
        this.message = message;
    }
}

