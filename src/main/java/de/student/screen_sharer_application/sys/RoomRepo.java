package de.student.screen_sharer_application.sys;

import de.student.screen_sharer_application.room.RoomEntity;
import de.student.screen_sharer_application.services.*;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class RoomRepo implements IRoomRepo {

    public record RoomData(String roomID, String adminID, Integer roomSize , String roomName){
    }
    public record RoomUserData(String userID, String roomName,String roomID){
    }

    private final JdbcTemplate template;

    public RoomRepo(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void createRoom(RoomEntity roomEntity) {
        template.update("INSERT INTO Room_Data values(?,?,?,?)",ps -> {
            ps.setString(1,roomEntity.getRoomID().toString());
            ps.setString(2,roomEntity.getAdminID().toString());
            ps.setInt(3,roomEntity.getRoomSize());
            ps.setString(4,roomEntity.getRoomName());
        });
        template.update("INSERT INTO Room_User_Data (userID,roomNAME,roomID) values (?,?,?)",
                ps -> {
                    ps.setString(1,roomEntity.getAdminID().toString());
                    ps.setString(2,roomEntity.getUserIDs().get(0).roomName());
                    ps.setString(3,roomEntity.getRoomID().toString());
                });
    }

    @Override
    public RoomEntity loadRoom(UUID roomID) {
        List<RoomTransferData> userIds = template.query("select userID,roomName from Room_User_Data where roomID = ?", ps -> {
            ps.setString(1, roomID.toString());
        }, (rs, i) -> new RoomTransferData(UUID.fromString(rs.getString(1)),rs.getString(2)));

        return template.query("select * from Room_Data where roomID = ?",ps -> {
            ps.setString(1,roomID.toString());
        },(rs,i) -> RoomEntity.load(rs.getString(4),rs.getInt(3),
                UUID.fromString(rs.getString(2)),userIds,
                UUID.fromString(rs.getString(1)))).get(0);
    }

    public int getMaxRooms(){
        return template.query("SELECT * from Room_Data",new DataClassRowMapper<>(RoomData.class)).size();
    }

    public int getMaxUserDataOfRoom(){
        return template.query("select userID,roomNAME,roomID from Room_User_Data",
                new DataClassRowMapper<>(RoomUserData.class)).size();
    }

    @Override
    public void removeRoom(UUID roomID) {
        template.update("delete from Room_User_Data where roomID = ?",ps -> {
            ps.setString(1,roomID.toString());
        });
        template.update("delete from Room_Data where roomID = ?",ps -> {
            ps.setString(1,roomID.toString());
        });
    }

    @Override
    public void removeUser(UUID userID, UUID roomID) {
        template.update("delete from Room_User_Data where userID = ? and roomID = ?",ps -> {
            ps.setString(1,userID.toString());
            ps.setString(2,roomID.toString());
        });
        checkIfEmpty(roomID);
    }

    private void checkIfEmpty(UUID roomID){
        List<RoomUserData> roomUserData = template.query("select userID,roomNAME,roomID from Room_User_Data where roomID = ?", ps -> {
            ps.setString(1, roomID.toString());
        }, new DataClassRowMapper<>(RoomUserData.class));
        if(roomUserData.size() == 0){
            deleteAllInvites(roomID);
            template.update("delete from Room_Data where roomID = ?",ps -> ps.setString(1,roomID.toString()));
        }
    }

    // it's necessary to delete all stuff associated to the room !!!
    private void deleteAllInvites(UUID roomID){
        template.update("delete from Room_Invite_Data where roomID = ?",ps ->
                ps.setString(1,roomID.toString()));
    }

    @Override
    public void addUser(RoomTransferData roomTransferData, UUID roomID) {
        template.update("insert into Room_User_Data (userID,roomName,roomID) values(?,?,?)",ps -> {
            ps.setString(1,roomTransferData.userID().toString());
            ps.setString(2,roomTransferData.roomName());
            ps.setString(3,roomID.toString());
        });
    }

    // This method should be called if the admin gives the admin role to another user
    // other szenario is when admin leaves the room, and a new user has to become admin
    @Override
    public void changeAdminUser(UUID newAdminID, UUID roomID) {
        template.update("update Room_Data set adminID = ? where roomID = ?", ps -> {
            ps.setString(1, newAdminID.toString());
            ps.setString(2, roomID.toString());
        });
    }

    @Override
    public UUID retrieveAdmin(UUID roomID) {
        return template.query("select adminID from Room_Data where roomID = ?", ps ->
                ps.setString(1, roomID.toString()), RoomRepo::mapToUUID);
    }

    @Override
    public void inviteUser(LocalDateTime time, LocalDateTime expire, InviteRoomData inviteRoomData) {
        template.update("insert into Room_Invite_Data " +
                "(friendID,roomID,timeCreation,expire) values (?,?,?,?)", ps -> {
            ps.setString(1,inviteRoomData.friendID().toString());
            ps.setString(2,inviteRoomData.roomID().toString());
            ps.setString(3, Timestamp.valueOf(time).toString());
            ps.setString(4, Timestamp.valueOf(expire).toString());
        });
    }

    @Override
    public UserRoomData retrieveUser(UUID roomID, UUID userID) {
        return template.query("select userID,roomNAME from Room_User_Data where roomID = ? and userID = ?",ps -> {
            ps.setString(1,roomID.toString());
            ps.setString(2,userID.toString());
        }, new DataClassRowMapper<>(UserRoomData.class)).get(0);
    }

    @Override
    public List<RoomInfo> getAllRooms() {
        List<RoomData> rooms = template.query("select * from Room_Data", new DataClassRowMapper<>(RoomData.class));
        List<RoomInfo> roomInfoData = new ArrayList<>();
        for(RoomData rd : rooms){
            List<RoomUserData> roomUserData = template.query("select userID,roomNAME,roomID from Room_User_Data where roomID = ?", ps -> {
                ps.setString(1, rd.roomID);
            }, new DataClassRowMapper<>(RoomUserData.class));
            String s = roomUserData.stream()
                    .filter(x -> x.userID.equals(rd.adminID)).findAny().map(x -> x.roomName).orElse(null);
            roomInfoData.add(new RoomInfo(s,roomUserData.size(),rd.roomSize,rd.roomName));
        }
        return roomInfoData;
    }

    private static UUID mapToUUID(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return UUID.fromString(rs.getString(1));
        }
        return null;
    }
}
