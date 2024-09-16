package de.student.screen_sharer_application.services;

import java.util.regex.Pattern;

public record RegistrationData(String username, String password, String passwordRepeat) {

    public boolean hasErrors() {
        return Pattern.compile("[^a-zA-Z0-9]").matcher(username).find() || !password.equals(passwordRepeat);
    }
}
