package de.student.screen_sharer_application.services;

import de.student.screen_sharer_application.room.RoomEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.mockito.Mockito.*;

public class RoomServiceTest {

    IRoomRepo roomRepo = mock(IRoomRepo.class);
    RoomService roomService = new RoomService(roomRepo);

    @Test
    @DisplayName("User kann dem Raum beitreten, da Infos richtig sind")
    public void test_1(){
        UUID roomID = UUID.randomUUID();
        UUID userID = UUID.randomUUID();

        when(roomRepo.loadRoom(roomID)).thenReturn(RoomEntity.load("scaryroom",4,UUID.randomUUID()
                , new ArrayList<>(),roomID));

        RoomStatus roomStatus = roomService.joinRoom(new JoiningRoomData(roomID,userID,"sandro"));

        verify(roomRepo,times(1)).addUser(any(),any());

        assertThat(roomStatus).isEqualTo(RoomStatus.SUCCESS);
    }

    @Test
    @DisplayName("User kann dem nicht Raum beitreten, da die maximale Kapazität erreicht ist")
    public void test_2(){
        UUID roomID = UUID.randomUUID();
        UUID userID = UUID.randomUUID();
        List<RoomTransferData> ids = new ArrayList<>();
        ids.add(new RoomTransferData(UUID.randomUUID(),"abc"));
        ids.add(new RoomTransferData(UUID.randomUUID(),"def"));
        ids.add(new RoomTransferData(UUID.randomUUID(),"ghi"));
        ids.add(new RoomTransferData(UUID.randomUUID(),"jkl"));

        when(roomRepo.loadRoom(roomID)).thenReturn(RoomEntity.load("scaryroom",4,UUID.randomUUID()
                ,ids,roomID));

        RoomStatus roomStatus = roomService.joinRoom(new JoiningRoomData(roomID, userID,"hans"));

        verify(roomRepo,never()).addUser(any(),any());

        assertThat(roomStatus).isEqualTo(RoomStatus.MAX_CAP);
    }

    @Test
    @DisplayName("User kann dem Raum nicht beitreten, da der Raum nicht existiert")
    public void test_3(){
        UUID roomID = UUID.randomUUID();
        UUID userID = UUID.randomUUID();

        when(roomRepo.loadRoom(roomID)).thenReturn(null);

        RoomStatus roomStatus = roomService.joinRoom(new JoiningRoomData(roomID, userID,"hans"));

        verify(roomRepo,never()).addUser(any(),any());

        assertThat(roomStatus).isEqualTo(RoomStatus.NOT_EXISTING);
    }

    @Test
    @DisplayName("Ein Benutzer wird zu dem Raum eingeladen und ein Platz wird für diesen reserviert")
    public void test_4(){
        UUID roomID = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();
        LocalDateTime t1 = LocalDateTime.of(2010,10,10,10,10,10);
        LocalDateTime t2 = LocalDateTime.of(2010,10,10,10,25,10);
        List<RoomTransferData> u = List.of(new RoomTransferData(UUID.randomUUID(),"admin"));
        RoomEntity roomEntity = RoomEntity.load("chickenroom",5,UUID.randomUUID(),u,roomID);
        InviteRoomData inviteRoomData = new InviteRoomData(roomID,friendID);

        when(roomRepo.loadRoom(inviteRoomData.roomID())).thenReturn(roomEntity);

        roomService.inviteToRoom(t1,t2,inviteRoomData);

        verify(roomRepo,times(1)).inviteUser(t1,t2,inviteRoomData);
    }

    @Test
    @DisplayName("Der Raum ist bereits voll. Der Benutzer kann dem Raum nicht beitreten")
    public void test_5(){
        UUID roomID = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();
        LocalDateTime t1 = LocalDateTime.of(2010,10,10,10,10,10);
        LocalDateTime t2 = LocalDateTime.of(2010,10,10,10,25,10);
        List<RoomTransferData> u = List.of(new RoomTransferData(UUID.randomUUID(),"admin"));
        RoomEntity roomEntity = RoomEntity.load("chickenroom",1,UUID.randomUUID(),u,roomID);
        InviteRoomData inviteRoomData = new InviteRoomData(roomID,friendID);

        when(roomRepo.loadRoom(inviteRoomData.roomID())).thenReturn(roomEntity);

        roomService.inviteToRoom(t1,t2,inviteRoomData);

        verify(roomRepo,times(0)).inviteUser(t1,t2,inviteRoomData);
    }

    @Test
    @DisplayName("Benutzer kann dem Raum beitreten und Raumkapazität wird erreicht. Nächste Anfrage ist somit nicht mehr " +
            "möglich")
    public void test_6(){
        UUID roomID = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();
        UUID friendID2 = UUID.randomUUID();
        LocalDateTime t1 = LocalDateTime.of(2010,10,10,10,10,10);
        LocalDateTime t2 = LocalDateTime.of(2010,10,10,10,25,10);
        List<RoomTransferData> u = List.of(new RoomTransferData(UUID.randomUUID(),"admin"));
        List<RoomTransferData> u2 = List.of(new RoomTransferData(UUID.randomUUID(),"admin"),
                new RoomTransferData(UUID.randomUUID(),"user2"));
        RoomEntity roomEntity = RoomEntity.load("chickenroom",2,UUID.randomUUID(),u,roomID);
        RoomEntity updated = RoomEntity.load("chickenroom",2,UUID.randomUUID(),u2,roomID);
        InviteRoomData inviteRoomData = new InviteRoomData(roomID,friendID);
        InviteRoomData inviteRoomData1 = new InviteRoomData(roomID,friendID2);

        when(roomRepo.loadRoom(inviteRoomData.roomID())).thenReturn(roomEntity,updated);

        roomService.inviteToRoom(t1,t2,inviteRoomData);
        roomService.inviteToRoom(t1,t2,inviteRoomData1);

        verify(roomRepo,times(1)).inviteUser(t1,t2,inviteRoomData);
    }

    @Test
    @DisplayName("Removing ist erlaubt und der User wird removed")
    public void test_7(){
        UUID adminID = UUID.randomUUID();
        UUID roomID = UUID.randomUUID();
        UUID removeUserID = UUID.randomUUID();
        RemoveUserData removeUserData = new RemoveUserData(removeUserID,adminID,roomID);

        when(roomRepo.loadRoom(roomID)).thenReturn(RoomEntity.load("aroom",4,adminID,List.of(
                new RoomTransferData(adminID,"adminuser"),
                new RoomTransferData(removeUserID,"auser")),roomID
        ));
        RemoveStatus removeStatus = roomService.removeUser(removeUserData);

        assertThat(removeStatus).isEqualTo(RemoveStatus.ALLOWED);

        verify(roomRepo,times(1)).removeUser(removeUserID,roomID);
    }

    @Test
    @DisplayName("Removing ist nicht erlaubt und somit bleibt der Raum gleich")
    public void test_8(){
        UUID adminID = UUID.randomUUID();
        UUID fakeAdminID = UUID.randomUUID();
        UUID userID = UUID.randomUUID();
        UUID roomID = UUID.randomUUID();
        UUID removeUserID = UUID.randomUUID();
        RemoveUserData removeUserData = new RemoveUserData(removeUserID,fakeAdminID,roomID);

        when(roomRepo.loadRoom(roomID)).thenReturn(RoomEntity.load("aroom",4,adminID,List.of(
                new RoomTransferData(adminID,"adminuser"),
                new RoomTransferData(userID,"auser")),roomID
        ));
        RemoveStatus removeStatus = roomService.removeUser(removeUserData);

        assertThat(removeStatus).isEqualTo(RemoveStatus.NOT_ALLOWED);

        verify(roomRepo,times(0)).removeUser(removeUserID,roomID);
    }
}
