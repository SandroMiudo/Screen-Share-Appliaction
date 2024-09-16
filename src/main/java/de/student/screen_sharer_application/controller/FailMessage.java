package de.student.screen_sharer_application.controller;

public class FailMessage implements ResponseMessage{

    private String raw;

    @Override
    public <T> void rawResponseMessage(T t) {
        raw = String.format("%s failed.",t.toString());
    }

    public String getRaw() {
        return raw;
    }
}
