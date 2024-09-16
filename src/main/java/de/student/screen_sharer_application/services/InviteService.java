package de.student.screen_sharer_application.services;

import de.student.screen_sharer_application.util.TimeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InviteService {

    private final IInviteRepo inviteRepo;

    public InviteService(IInviteRepo inviteRepo) {
        this.inviteRepo = inviteRepo;
    }

    public List<InviteRoomData> retrieveInvites(UUID userID){
        return inviteRepo.getInvites(userID);
    }

    public void removeExpiredInvites(LocalDateTime time){
        inviteRepo.removeInvites(time);
    }
}
