package de.student.screen_sharer_application.sys;

import de.student.screen_sharer_application.room.RoomEntity;
import de.student.screen_sharer_application.services.RoomInfo;
import de.student.screen_sharer_application.services.RoomTransferData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RoomRepoTest {

    @Autowired
    RoomRepo roomRepo;

    @Test
    @Sql({"classpath:sql-files/example_data_room_repo.sql"})
    @DisplayName("Beim erzeugen eines Raumes wird der Raum und der Admin in der Db gespeichert")
    public void test_1(){
        RoomEntity roomEntity = RoomEntity.create("abc",1, UUID.randomUUID());
        roomEntity.addUser(UUID.randomUUID(),"adminsandro");
        roomRepo.createRoom(roomEntity);

        assertThat(roomRepo.getMaxRooms()).isEqualTo(1);
        assertThat(roomRepo.getMaxUserDataOfRoom()).isEqualTo(1);
    }

    @Test
    @Sql({"classpath:sql-files/example_data_room_repo.sql"})
    @DisplayName("Beim erzeugen zweier Räume wird der Raum und der Admin in der Db gespeichert")
    public void test_2(){
        RoomEntity roomEntity = RoomEntity.create("abc",1, UUID.randomUUID());
        RoomEntity roomEntity1 = RoomEntity.create("def",1,UUID.randomUUID());
        roomEntity.addUser(UUID.randomUUID(),"adminroom1");
        roomEntity1.addUser(UUID.randomUUID(),"adminroom2");
        roomRepo.createRoom(roomEntity);
        roomRepo.createRoom(roomEntity1);

        assertThat(roomRepo.getMaxRooms()).isEqualTo(2);
        assertThat(roomRepo.getMaxUserDataOfRoom()).isEqualTo(2);
    }

    @Test
    @Sql({"classpath:sql-files/example_data_room_repo.sql"})
    @DisplayName("Ein User wird einem Raum hinzugefügt")
    public void test_3(){
        RoomEntity roomEntity = RoomEntity.create("abc",5, UUID.randomUUID());
        roomEntity.addUser(UUID.randomUUID(),"adminroom1");
        UUID roomID = roomEntity.getRoomID();
        roomRepo.createRoom(roomEntity);

        roomRepo.addUser(new RoomTransferData(UUID.randomUUID(),"hans"),roomID);

        assertThat(roomRepo.getMaxRooms()).isEqualTo(1);
        assertThat(roomRepo.getMaxUserDataOfRoom()).isEqualTo(2);
    }

    @Test
    @Sql({"classpath:sql-files/example_data_room_repo.sql"})
    @DisplayName("2 Räume mit each 3 Nutzers werden erzeugt")
    public void test_4(){
        RoomEntity roomEntity = RoomEntity.create("abc",5, UUID.randomUUID());
        RoomEntity roomEntity1 = RoomEntity.create("def",5,UUID.randomUUID());
        roomEntity.addUser(UUID.randomUUID(),"adminroom1");
        roomEntity1.addUser(UUID.randomUUID(),"adminroom2");
        UUID roomID = roomEntity.getRoomID();
        UUID roomID2 = roomEntity1.getRoomID();

        roomRepo.createRoom(roomEntity);
        roomRepo.createRoom(roomEntity1);

        roomRepo.addUser(new RoomTransferData(UUID.randomUUID(),"hans"),roomID);
        roomRepo.addUser(new RoomTransferData(UUID.randomUUID(),"peter"),roomID);

        roomRepo.addUser(new RoomTransferData(UUID.randomUUID(),"hans"),roomID2);
        roomRepo.addUser(new RoomTransferData(UUID.randomUUID(),"peter"),roomID2);

        assertThat(roomRepo.getMaxRooms()).isEqualTo(2);
        assertThat(roomRepo.getMaxUserDataOfRoom()).isEqualTo(6);
    }

    @Test
    @Sql({"classpath:sql-files/example_data_room_repo.sql"})
    @DisplayName("Ein Raum und alle User die im Raum waren, werden aus der DB gelöscht")
    public void test_5(){
        RoomEntity roomEntity = RoomEntity.create("abc",1, UUID.randomUUID());
        RoomEntity roomEntity1 = RoomEntity.create("def",1,UUID.randomUUID());
        roomEntity.addUser(UUID.randomUUID(),"adminroom1");
        roomEntity1.addUser(UUID.randomUUID(),"adminroom2");

        roomRepo.createRoom(roomEntity);
        roomRepo.createRoom(roomEntity1);

        roomRepo.removeRoom(roomEntity.getRoomID());

        assertThat(roomRepo.getMaxRooms()).isEqualTo(1);
        assertThat(roomRepo.getMaxUserDataOfRoom()).isEqualTo(1);
    }

    @Test
    @Sql({"classpath:sql-files/example_data_room_repo.sql"})
    @DisplayName("Alle 3 Räume und deren User werden gelöscht")
    public void test_6(){
        RoomEntity roomEntity = RoomEntity.create("abcg",1, UUID.randomUUID());
        RoomEntity roomEntity1 = RoomEntity.create("defe",1,UUID.randomUUID());
        RoomEntity roomEntity2 = RoomEntity.create("defg",1,UUID.randomUUID());

        roomEntity.addUser(UUID.randomUUID(),"adminroom1");
        roomEntity1.addUser(UUID.randomUUID(),"adminroom2");
        roomEntity2.addUser(UUID.randomUUID(),"adminroom3");

        roomRepo.createRoom(roomEntity);
        roomRepo.createRoom(roomEntity1);
        roomRepo.createRoom(roomEntity2);

        roomRepo.removeRoom(roomEntity.getRoomID());
        roomRepo.removeRoom(roomEntity1.getRoomID());
        roomRepo.removeRoom(roomEntity2.getRoomID());

        assertThat(roomRepo.getMaxRooms()).isEqualTo(0);
        assertThat(roomRepo.getMaxUserDataOfRoom()).isEqualTo(0);
    }

    @Test
    @Sql({"classpath:sql-files/example_data_room_repo.sql"})
    @DisplayName("Ein User wird aus dem Raum entfernt")
    public void test_7(){
        RoomEntity roomEntity = RoomEntity.create("abc",3, UUID.randomUUID());
        roomEntity.addUser(UUID.randomUUID(),"adminroom1");

        UUID delUserId = UUID.randomUUID();
        roomRepo.createRoom(roomEntity);
        roomRepo.addUser(new RoomTransferData(UUID.randomUUID(),"hans"),roomEntity.getRoomID());
        roomRepo.addUser(new RoomTransferData(delUserId,"florm"),roomEntity.getRoomID());

        roomRepo.removeUser(delUserId,roomEntity.getRoomID());

        assertThat(roomRepo.getMaxRooms()).isEqualTo(1);
        assertThat(roomRepo.getMaxUserDataOfRoom()).isEqualTo(2);
    }

    @Test
    @Sql({"classpath:sql-files/example_data_room_repo.sql"})
    @DisplayName("Falls der letzte User aus dem Raum gehen sollte dann wird der Raum ebenfalls gelöscht")
    public void test_8(){
        RoomEntity roomEntity = RoomEntity.create("abc",1, UUID.randomUUID());
        roomEntity.addUser(UUID.randomUUID(),"adminroom1");

        roomRepo.createRoom(roomEntity);

        roomRepo.removeUser(roomEntity.getAdminID(),roomEntity.getRoomID());

        assertThat(roomRepo.getMaxRooms()).isEqualTo(0);
        assertThat(roomRepo.getMaxUserDataOfRoom()).isEqualTo(0);
    }

    @Test
    @Sql({"classpath:sql-files/example_data_room_repo.sql"})
    @DisplayName("Neuer User aus dem Raum bekommt die Admin Rechte")
    public void test_9(){
        RoomEntity roomEntity = RoomEntity.create("abc",1, UUID.randomUUID());
        roomEntity.addUser(UUID.randomUUID(),"adminroom1");

        UUID newAdminUser = UUID.randomUUID();
        roomRepo.createRoom(roomEntity);
        roomRepo.addUser(new RoomTransferData(UUID.randomUUID(),"hans"),roomEntity.getRoomID());
        roomRepo.addUser(new RoomTransferData(UUID.randomUUID(),"peter"),roomEntity.getRoomID());
        roomRepo.addUser(new RoomTransferData(newAdminUser,"florian"),roomEntity.getRoomID());

        roomRepo.changeAdminUser(newAdminUser,roomEntity.getRoomID());
        UUID adminID = roomRepo.retrieveAdmin(roomEntity.getRoomID());

        assertThat(adminID).isEqualByComparingTo(newAdminUser);
    }

    @Test
    @DisplayName("Ein Raum wird geladen")
    @Sql({"classpath:sql-files/example_data_room_repo2.sql"})
    public void test_10(){
        RoomEntity room = roomRepo.loadRoom(UUID.fromString("02185388-afd5-422f-8ea6-3edbaaa72e9c"));
        assertThat(room.getRoomSize()).isEqualTo(4);
        assertThat(room.getRoomName()).isEqualTo("scaryroom");
        assertThat(room.getAdminID()).isEqualTo(UUID.fromString("c139f461-315b-403e-a77b-8fde52f4cefa"));
        assertThat(room.getRoomID()).isEqualTo(UUID.fromString("02185388-afd5-422f-8ea6-3edbaaa72e9c"));
        assertThat(room.getUserIDs()).isEqualTo(
                List.of(new RoomTransferData(UUID.fromString("c139f461-315b-403e-a77b-8fde52f4cefa"),"sandro")));
    }

    @Test
    @DisplayName("Ein Raum mit 3 Usern wird geladen")
    @Sql({"classpath:sql-files/example_data_room_repo2.sql"})
    public void test_11(){
        UUID roomID = UUID.fromString("02185388-afd5-422f-8ea6-3edbaaa72e9c");
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        roomRepo.addUser(new RoomTransferData(user1,"hans"),roomID);
        roomRepo.addUser(new RoomTransferData(user2,"peter"),roomID);

        RoomEntity room = roomRepo.loadRoom(roomID);

        assertThat(room.getRoomSize()).isEqualTo(4);
        assertThat(room.getRoomName()).isEqualTo("scaryroom");
        assertThat(room.getAdminID()).isEqualTo(UUID.fromString("c139f461-315b-403e-a77b-8fde52f4cefa"));
        assertThat(room.getRoomID()).isEqualTo(UUID.fromString("02185388-afd5-422f-8ea6-3edbaaa72e9c"));
        assertThat(room.getUserIDs()).isEqualTo(List.of(new RoomTransferData(
                UUID.fromString("c139f461-315b-403e-a77b-8fde52f4cefa"),"sandro"),
                new RoomTransferData(user1,"hans"),new RoomTransferData(user2,"peter")));
    }

    @Sql({"classpath:sql-files/example_data_room_repo3.sql"})
    @Test
    @DisplayName("")
    public void test_12(){
        List<RoomInfo> allRooms = roomRepo.getAllRooms();
        RoomInfo roomInfo = allRooms.get(0);

        assertThat(roomInfo.roomName()).isEqualTo("scaryroom");
        assertThat(roomInfo.size()).isEqualTo(4);
        assertThat(roomInfo.currentSize()).isEqualTo(3);
        assertThat(roomInfo.admin()).isEqualTo("sandro");
    }

    @Sql({"classpath:sql-files/example_data_room_repo3.sql"})
    @Test
    @DisplayName("")
    public void test_13(){
        List<RoomInfo> allRooms = roomRepo.getAllRooms();
        RoomInfo roomInfo = allRooms.get(0);
        RoomInfo roomInfo1 = allRooms.get(1);

        assertThat(roomInfo.roomName()).isEqualTo("scaryroom");
        assertThat(roomInfo.size()).isEqualTo(4);
        assertThat(roomInfo.currentSize()).isEqualTo(3);
        assertThat(roomInfo.admin()).isEqualTo("sandro");

        assertThat(roomInfo1.roomName()).isEqualTo("hunts");
        assertThat(roomInfo1.size()).isEqualTo(3);
        assertThat(roomInfo1.currentSize()).isEqualTo(3);
        assertThat(roomInfo1.admin()).isEqualTo("sandra");
    }
}
