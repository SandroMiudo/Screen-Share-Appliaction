package de.student.screen_sharer_application.controller;

public class SuccessMessage implements ResponseMessage{

    private String raw;

    @Override
    public <T> void rawResponseMessage(T t) {
        raw = String.format("%s was successful.",t.toString());
    }

    public String getRaw() {
        return raw;
    }
}
