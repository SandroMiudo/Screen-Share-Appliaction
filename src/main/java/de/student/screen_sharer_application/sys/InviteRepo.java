package de.student.screen_sharer_application.sys;

import de.student.screen_sharer_application.services.IInviteRepo;
import de.student.screen_sharer_application.services.InviteRoomData;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class InviteRepo implements IInviteRepo {

    private final JdbcTemplate template;

    public InviteRepo(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<InviteRoomData> getInvites(UUID friendID) {
        return template.query("select friendID,roomID from Room_Invite_Data where " +
                "friendID = ?",ps -> {
            ps.setString(1,friendID.toString());
        },new DataClassRowMapper<>(InviteRoomData.class));
    }

    @Override
    public void removeInvites(LocalDateTime currentTime) {
        template.update("delete from Room_Invite_Data where expire < ?",ps -> {
            ps.setTimestamp(1, Timestamp.valueOf(currentTime));
        });
    }
}
