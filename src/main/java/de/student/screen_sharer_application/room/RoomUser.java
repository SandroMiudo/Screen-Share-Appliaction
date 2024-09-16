package de.student.screen_sharer_application.room;

import java.util.UUID;

class RoomUser {
    private final UUID userID;
    private String roomName;

    public RoomUser(UUID userID, String roomName) {
        this.userID = userID;
        this.roomName = roomName;
    }

    public boolean containsID(UUID cmpID){
        return userID.equals(cmpID);
    }

    public UUID getUserID() {
        return userID;
    }

    public String getRoomName() {
        return roomName;
    }
}
