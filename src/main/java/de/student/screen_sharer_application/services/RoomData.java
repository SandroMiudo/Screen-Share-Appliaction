package de.student.screen_sharer_application.services;

import java.util.UUID;

public record RoomData(String roomname , Integer roomsize,
                       UUID adminuser, String username){
    // validation with javax is not working
    public boolean hasErrors(){
        return !(roomname.length() >= 4 && roomname.length() <= 16 && roomsize >= 0 && roomsize <= 5 && adminuser != null
                && username.length() >= 4 && username.length() <= 30);
    }
}
