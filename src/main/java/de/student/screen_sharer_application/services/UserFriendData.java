package de.student.screen_sharer_application.services;

import java.util.UUID;

public record UserFriendData(UUID userid, UUID friendid, String friendname) {
    public boolean hasErrors(){
        return !(userid != null && friendid != null && friendname.length() >= 4 && friendname.length() <= 36);
    }
}
