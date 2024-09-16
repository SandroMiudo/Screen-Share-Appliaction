package de.student.screen_sharer_application.room;

import de.student.screen_sharer_application.services.InviteRoomData;
import de.student.screen_sharer_application.services.RoomTransferData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RoomEntity {
    private UUID roomID;
    private UUID adminID;
    private List<RoomUser> userIDs;
    private final List<UUID> reservedInvites = new ArrayList<>();
    private Integer roomSize;
    private String roomName;
    private Integer loadingSize;

    private RoomEntity(String roomName, Integer roomSize, UUID adminID) {
        this.roomName = roomName;
        this.roomSize = roomSize;
        this.adminID = adminID;
    }

    private RoomEntity(String roomName, Integer roomSize , UUID adminID,
                       List<RoomUser> userIDs, UUID roomID){
        this.roomName = roomName;
        this.roomSize = roomSize;
        this.adminID = adminID;
        this.userIDs = new ArrayList<>(userIDs);
        this.roomID = roomID;
        this.loadingSize = userIDs.size();
    }

    public static RoomEntity create(String roomName, Integer roomSize, UUID adminID){
        RoomEntity roomEntity = new RoomEntity(roomName, roomSize, adminID);
        roomEntity.generateRoomData();
        return roomEntity;
    }

    public static RoomEntity load(String roomName, Integer roomSize, UUID adminID,
                                  List<RoomTransferData> userIDs, UUID roomID){
        return new RoomEntity(roomName,roomSize,adminID,mapTransferDataToUser(userIDs),roomID);
    }

    private static List<RoomUser> mapTransferDataToUser(List<RoomTransferData> roomTransferData){
        return roomTransferData.stream().map(x -> new RoomUser(x.userID(),x.roomName())).toList();
    }

    private void generateRoomData(){
        this.roomID = UUID.randomUUID();
        this.userIDs = new ArrayList<>();
    }

    public void addUser(UUID userID, String roomName){
        if(userIDs.size() + reservedInvites.size() >= roomSize) return;
        userIDs.add(new RoomUser(userID,roomName));
    }

    public boolean invitePossible(UUID userID){
        int oldLength = reservedInvites.size();
        if ((userIDs.size()+reservedInvites.size()) < roomSize && !reservedInvites.contains(userID)
                && userIDs.stream().noneMatch(x -> x.containsID(userID))){
            reservedInvites.add(userID);
        }
        return oldLength != reservedInvites.size();
    }

    public boolean isSameSizeAsBefore(){
        return loadingSize == userIDs.size();
    }

    public void removeUser(UUID userID, UUID adminID){
        if(!adminID.equals(this.adminID)) return;
        Optional<RoomUser> optionalRoomUser = userIDs.stream().filter(x -> x.containsID(userID)).findAny();
        if(optionalRoomUser.isEmpty()) return;
        userIDs.remove(optionalRoomUser.get());
    }

    public Integer changeRoomSize(Integer newSize){
        this.roomSize = newSize;
        return this.roomSize;
    }

    public String changeRoomName(String newRoomName){
        this.roomName = newRoomName;
        return this.roomName;
    }

    public UUID changeAdminUser(UUID newAdminUser, UUID currentAdminUser){
        if(!currentAdminUser.equals(adminID)) return null;
        this.adminID = newAdminUser;
        return this.adminID;
    }
    public boolean userJoined(UUID userID){
        return userIDs.stream().map(RoomUser::getUserID).anyMatch(x -> x.equals(userID));
    }

    public UUID getRoomID() {
        return roomID;
    }

    public UUID getAdminID() {
        return adminID;
    }

    public List<RoomTransferData> getUserIDs() {
        return userIDs.stream().map(x -> new RoomTransferData(x.getUserID(),x.getRoomName())).toList();
    }

    public Integer getRoomSize() {
        return roomSize;
    }

    public String getRoomName() {
        return roomName;
    }
}
