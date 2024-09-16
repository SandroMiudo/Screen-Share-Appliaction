package de.student.screen_sharer_application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IInviteRepo {
    public List<InviteRoomData> getInvites(UUID userID);
    public void removeInvites(LocalDateTime currentTime);
}
