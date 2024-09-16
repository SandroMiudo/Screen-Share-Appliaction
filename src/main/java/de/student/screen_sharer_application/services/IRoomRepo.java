package de.student.screen_sharer_application.services;

import de.student.screen_sharer_application.room.RoomEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IRoomRepo {
    public void createRoom(RoomEntity roomEntity);
    public RoomEntity loadRoom(UUID roomID);
    public void removeRoom(UUID roomID);
    public void removeUser(UUID userID, UUID roomID);
    public void addUser(RoomTransferData transferData, UUID roomID);
    public void changeAdminUser(UUID newAdminID, UUID roomID);
    public UUID retrieveAdmin(UUID roomID);
    public void inviteUser(LocalDateTime time,LocalDateTime expire,
                           InviteRoomData inviteRoomData);
    public UserRoomData retrieveUser(UUID roomID, UUID userID);
    public List<RoomInfo> getAllRooms();
}
