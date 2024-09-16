package de.student.screen_sharer_application.services;

import java.util.UUID;
import java.util.regex.Pattern;

public record JoiningRoomData(UUID roomCode, UUID uservalue, String roomname) {
    public boolean hasErrors(){
        return !(correctPattern() && roomname.length() >= 4 && roomname.length() <= 30 &&
                roomCode != null && uservalue != null);
    }

    private boolean correctPattern(){
        return !Pattern.compile("[^a-zA-Z]").matcher(roomname).find();
    }
}
