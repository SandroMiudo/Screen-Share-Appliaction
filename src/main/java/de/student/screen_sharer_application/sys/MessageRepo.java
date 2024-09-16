package de.student.screen_sharer_application.sys;

import de.student.screen_sharer_application.services.IMessageRepo;
import de.student.screen_sharer_application.services.MessageData;
import de.student.screen_sharer_application.services.RoomTransferData;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public class MessageRepo implements IMessageRepo {

    private final JdbcTemplate template;

    public MessageRepo(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void send(MessageData messageData, RoomTransferData roomTransferData) {
        template.update("insert into Message_Data (rawmessage,sendtime,userID,roomID,roomName) " +
                "values(?,?,?,?,?)", ps -> {
            ps.setString(1,messageData.rawmessage());
            ps.setTimestamp(2, Timestamp.valueOf(messageData.sendtime()));
            ps.setString(3,messageData.userID().toString());
            ps.setString(4,messageData.roomID().toString());
            ps.setString(5,roomTransferData.roomName());
        });
    }

    @Override
    public List<MessageData> getAllMessagesOfRoom(UUID roomID) {
        return template.query("select * from Message_Data where roomID = ?", ps -> {
            ps.setString(1,roomID.toString());
        }, new DataClassRowMapper<>(MessageData.class));
    }
}
