package de.student.screen_sharer_application.controller;

public interface ResponseMessage {
    public <T> void rawResponseMessage(T t);
    public String getRaw();
}
