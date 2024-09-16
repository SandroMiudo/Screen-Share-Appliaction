package de.student.screen_sharer_application.services;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageData(String rawmessage, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime sendtime,
                          UUID userID, UUID roomID, Integer messageID, String roomName) {
    public boolean hasErrors(){
        return !(rawmessage != null && !rawmessage.isEmpty() && userID != null && roomID != null &&
                roomName.isEmpty());
    }
}
