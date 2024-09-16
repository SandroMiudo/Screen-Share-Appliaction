package de.student.screen_sharer_application.services;

import de.student.screen_sharer_application.room.RoomEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService {

    private final IRoomRepo roomRepo;

    public RoomService(IRoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    public UUID createRoom(RoomData roomData){
        RoomEntity roomEntity = RoomEntity.create(roomData.roomname(),roomData.roomsize(),roomData.adminuser());
        roomEntity.addUser(roomData.adminuser(),roomData.username());
        roomRepo.createRoom(roomEntity);
        return roomEntity.getRoomID();
    }

    public RoomStatus joinRoom(JoiningRoomData roomData){
        RoomEntity roomEntity = roomRepo.loadRoom(roomData.roomCode());
        if(roomEntity == null){
            return RoomStatus.NOT_EXISTING;
        }
        roomEntity.addUser(roomData.uservalue(),roomData.roomname());
        if(roomEntity.userJoined(roomData.uservalue())){
            roomRepo.addUser(new RoomTransferData(roomData.uservalue(),roomData.roomname()),roomData.roomCode());
            return RoomStatus.SUCCESS;
        }
        return RoomStatus.MAX_CAP;
    }

    public RoomEntity getRoomInfo(UUID roomID){
        return roomRepo.loadRoom(roomID);
    }

    public RoomTransferData getUserRoomData(UUID roomID , UUID userID){
        RoomEntity roomEntity = roomRepo.loadRoom(roomID);
        Optional<RoomTransferData> roomuserdata = roomEntity.getUserIDs().stream()
                .filter(x -> x.userID().equals(userID)).findAny();
        return roomuserdata.orElse(null);
    }

    public void leaveRoom(LeavingRoomData leavingRoomData){
        roomRepo.removeUser(leavingRoomData.userID(),leavingRoomData.roomID());
    }

    public void inviteToRoom(LocalDateTime timeCreation, LocalDateTime expire,
                             InviteRoomData inviteRoomData){
        RoomEntity roomEntity = roomRepo.loadRoom(inviteRoomData.roomID());
        if(roomEntity.invitePossible(inviteRoomData.friendID())){
            roomRepo.inviteUser(timeCreation,expire,inviteRoomData);
        }
    }

    public UserRoomData getUser(UUID userID, UUID roomID){
        return roomRepo.retrieveUser(roomID,userID);
    }

    public RemoveStatus removeUser(RemoveUserData removeUserData){
        RoomEntity room = roomRepo.loadRoom(removeUserData.roomID());
        room.removeUser(removeUserData.removeUserID(),removeUserData.adminID());
        if(room.isSameSizeAsBefore()){
            return RemoveStatus.NOT_ALLOWED;
        }
        roomRepo.removeUser(removeUserData.removeUserID(),removeUserData.roomID());
        return RemoveStatus.ALLOWED;
    }

    public UUID changeAdmin(AdminChangeData adminChangeData){
        RoomEntity room = roomRepo.loadRoom(adminChangeData.roomID());
        UUID newAdminUser = room.changeAdminUser(adminChangeData.newAdmin(),adminChangeData.currentAdmin());
        if(newAdminUser == null) return null;
        roomRepo.changeAdminUser(newAdminUser,adminChangeData.roomID());
        return newAdminUser;
    }

    public List<RoomInfo> getAllRooms(){
        return roomRepo.getAllRooms();
    }
}
