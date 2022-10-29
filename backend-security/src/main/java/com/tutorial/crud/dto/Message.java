package com.tutorial.crud.dto;

public class Message {
    private String message;

    public Message(String mensaje) {
        this.message = mensaje;
    }

    public String getMensaje() {
        return message;
    }

    public void setMensaje(String mensaje) {
        this.message = mensaje;
    }
}

