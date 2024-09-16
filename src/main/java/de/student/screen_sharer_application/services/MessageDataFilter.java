package de.student.screen_sharer_application.services;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageDataFilter(UUID roomID, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time) {
    public boolean hasErrors(){
        return !(roomID != null && time != null);
    }
}
