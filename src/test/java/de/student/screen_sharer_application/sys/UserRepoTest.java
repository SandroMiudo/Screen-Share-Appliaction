package de.student.screen_sharer_application.sys;

import de.student.screen_sharer_application.services.UserFriendData;
import de.student.screen_sharer_application.user.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.COLLECTION;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepoTest {

    @Autowired
    UserRepo userRepo;

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("Beim 1-maligem speichern ist ein Eintrag in der Datenbank")
    public void test_1(){
        UserEntity userEntity = UserEntity.create("password","username",UUID.randomUUID());
        userRepo.saveUser(userEntity);

        int i = userRepo.getMaxRowsUserData();
        assertThat(i).isEqualTo(1);
    }

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("Beim 3-maligem speichern ist ein Eintrag in der Datenbank")
    public void test_2(){
        userRepo.saveUser(UserEntity.create("password1","username1",UUID.randomUUID()));
        userRepo.saveUser(UserEntity.create("password2","username2",UUID.randomUUID()));
        userRepo.saveUser(UserEntity.create("password3","username3",UUID.randomUUID()));

        int i = userRepo.getMaxRowsUserData();
        assertThat(i).isEqualTo(3);
    }

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("Beim 1-maligem speichern ist ein Eintrag in der Datenbank")
    public void test_3(){
        UserEntity userEntity = UserEntity.create("password", "username",UUID.randomUUID());
        userRepo.saveUser(userEntity);

        userEntity.addFriend(UUID.randomUUID(),"friend");
        userRepo.updateUserDetails(userEntity);


        int i = userRepo.getMaxRowsFriendData();
        int j = userRepo.getMaxRowsUserData();
        assertThat(i).isEqualTo(1);
        assertThat(j).isEqualTo(1);
    }

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("Beim 3-maligem speichern ist ein Eintrag in der Datenbank")
    public void test_4(){
        UserEntity userEntity = UserEntity.create("pass1", "user1",UUID.randomUUID());
        userRepo.saveUser(userEntity);

        userEntity.addFriend(UUID.randomUUID(),"user2");
        userEntity.addFriend(UUID.randomUUID(),"user3");
        userEntity.addFriend(UUID.randomUUID(),"user4");

        userRepo.updateUserDetails(userEntity);

        int i = userRepo.getMaxRowsFriendData();
        int j = userRepo.getMaxRowsUserData();
        assertThat(i).isEqualTo(3);
        assertThat(j).isEqualTo(1);
    }

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("Beim 5-maligem speichern ist ein Eintrag in der Datenbank")
    public void test_5(){
        UserEntity userEntity = UserEntity.create("pass1", "user1",UUID.randomUUID());
        userRepo.saveUser(userEntity);

        userEntity.addFriend(UUID.randomUUID(),"user2");
        userEntity.addFriend(UUID.randomUUID(),"user3");
        userEntity.addFriend(UUID.randomUUID(),"user4");
        userEntity.addFriend(UUID.randomUUID(),"user5");
        userEntity.addFriend(UUID.randomUUID(),"user6");

        userRepo.updateUserDetails(userEntity);

        int i = userRepo.getMaxRowsFriendData();
        int j = userRepo.getMaxRowsUserData();
        assertThat(i).isEqualTo(5);
        assertThat(j).isEqualTo(1);
    }

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("5 Freunde eines Nutzers werden angezeigt")
    public void test_6(){
        UUID userID = UUID.randomUUID();
        UserEntity userEntity = UserEntity.create("pass1", "user1",userID);
        userRepo.saveUser(userEntity);

        userEntity.addFriend(UUID.randomUUID(),"user2");
        userEntity.addFriend(UUID.randomUUID(),"user3");
        userEntity.addFriend(UUID.randomUUID(),"user4");
        userEntity.addFriend(UUID.randomUUID(),"user5");
        userEntity.addFriend(UUID.randomUUID(),"user6");

        userRepo.updateUserDetails(userEntity);

        List<UserFriendData> userFriendData = userRepo.getUser(userID).getFriends();

        assertThat(userFriendData).hasSize(5);
    }

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("Kontrolliere ob jeweils die richtigen Nutzer zurückgegeben werden")
    public void test_7(){
        UUID userID = UUID.randomUUID();
        UUID userID2 = UUID.randomUUID();
        UserEntity user1 = UserEntity.create("pass1", "user1", userID);
        UserEntity user2 = UserEntity.create("pass2", "user2", userID2);
        userRepo.saveUser(user1);
        userRepo.saveUser(user2);

        user1.addFriend(UUID.randomUUID(),"any1");
        user1.addFriend(UUID.randomUUID(),"any2");
        user1.addFriend(UUID.randomUUID(),"any3");
        user1.addFriend(UUID.randomUUID(),"any4");
        user1.addFriend(UUID.randomUUID(),"any5");

        user2.addFriend(UUID.randomUUID(),"any8");
        user2.addFriend(UUID.randomUUID(),"any9");

        userRepo.updateUserDetails(user1);
        userRepo.updateUserDetails(user2);

        List<UserFriendData> userFriendData = userRepo.getUser(userID).getFriends();
        List<UserFriendData> userFriendData2 = userRepo.getUser(userID2).getFriends();

        assertThat(userFriendData).hasSize(5);
        assertThat(userFriendData2).hasSize(2);

        assertThat(userFriendData2.get(0).friendname()).isEqualTo("any8");
        assertThat(userFriendData2.get(1).friendname()).isEqualTo("any9");
    }

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("Gibt den Freund any2 des users zurück")
    public void test_8(){
        UUID userID = UUID.randomUUID();
        UUID userID2 = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();
        UserEntity user1 = UserEntity.create("pass1", "user1", userID);
        UserEntity user2 = UserEntity.create("pass2", "user2", userID2);
        userRepo.saveUser(user1);
        userRepo.saveUser(user2);

        user1.addFriend(UUID.randomUUID(),"any1");
        user1.addFriend(UUID.randomUUID(),"any2");
        user1.addFriend(UUID.randomUUID(),"any3");
        user1.addFriend(UUID.randomUUID(),"any4");
        user1.addFriend(UUID.randomUUID(),"any5");

        user2.addFriend(friendID,"any8");
        user2.addFriend(UUID.randomUUID(),"any9");

        userRepo.updateUserDetails(user1);
        userRepo.updateUserDetails(user2);

        UserFriendData friend = userRepo.getUser(userID2).getFriend(friendID);

        assertThat(friend.friendname()).isEqualTo("any8");
        assertThat(friend.userid()).isEqualTo(userID2);
        assertThat(friend.friendid()).isEqualTo(friendID);
    }

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("Löscht den Freund any4 aus der Datenbank")
    public void test_9(){
        UUID userID = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();

        UserEntity userEntity = UserEntity.create("pass","username",userID);
        userRepo.saveUser(userEntity);

        userEntity.addFriend(UUID.randomUUID(),"any");
        userEntity.addFriend(UUID.randomUUID(),"any1");
        userEntity.addFriend(UUID.randomUUID(),"any2");
        userEntity.addFriend(UUID.randomUUID(),"any3");
        userEntity.addFriend(friendID,"any4");

        userRepo.updateUserDetails(userEntity);

        userEntity = userRepo.getUser(userID);

        userEntity.removeFriend(friendID);

        userRepo.updateUserDetails(userEntity);

        int i = userRepo.getMaxRowsFriendData();

        assertThat(i).isEqualTo(4);
    }

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("Löscht alle Freunde eines Nutzers")
    public void test_10(){
        UUID userID = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();
        UUID friendID2 = UUID.randomUUID();
        UUID friendID3 = UUID.randomUUID();
        UUID friendID4 = UUID.randomUUID();
        UUID friendID5 = UUID.randomUUID();
        UserEntity userEntity = UserEntity.create("pass","username",userID);
        userRepo.saveUser(userEntity);

        userEntity.addFriend(friendID,"any");
        userEntity.addFriend(friendID,"any1");
        userEntity.addFriend(friendID,"any2");
        userEntity.addFriend(friendID,"any3");
        userEntity.addFriend(friendID,"any4");

        userRepo.updateUserDetails(userEntity);

        userEntity.removeFriend(friendID);
        userEntity.removeFriend(friendID2);
        userEntity.removeFriend(friendID3);
        userEntity.removeFriend(friendID4);
        userEntity.removeFriend(friendID5);

        userRepo.updateUserDetails(userEntity);

        int i = userRepo.getMaxRowsFriendData();

        assertThat(i).isEqualTo(0);
    }

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("Gibt alle gespeicherten User zurück")
    public void test_11(){
        UserEntity userEntity = UserEntity.create("pass1","user1",UUID.randomUUID());
        UserEntity userEntity1 = UserEntity.create("pass1","user1",UUID.randomUUID());
        UserEntity userEntity2 = UserEntity.create("pass1","user1",UUID.randomUUID());

        userRepo.saveUser(userEntity);
        userRepo.saveUser(userEntity1);
        userRepo.saveUser(userEntity2);

        List<UserEntity> allUsers = userRepo.getAllUsers();

        assertThat(allUsers).hasSize(3);
    }

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("Gibt alle gespeicherten User zurück")
    public void test_12(){
        UserEntity userEntity = UserEntity.create("pass1","user1",UUID.randomUUID());
        UserEntity userEntity1 = UserEntity.create("pass2","user2",UUID.randomUUID());
        UserEntity userEntity2 = UserEntity.create("pass3","user3",UUID.randomUUID());
        UserEntity userEntity3 = UserEntity.create("pass4","user4",UUID.randomUUID());
        UserEntity userEntity4 = UserEntity.create("pass5","user5",UUID.randomUUID());

        userRepo.saveUser(userEntity);
        userRepo.saveUser(userEntity1);
        userRepo.saveUser(userEntity2);
        userRepo.saveUser(userEntity3);
        userRepo.saveUser(userEntity4);

        List<UserEntity> allUsers = userRepo.getAllUsers();

        assertThat(allUsers).hasSize(5);
    }

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("Sucht nach dem Usernamen user5 und gibt dann die UserDetails zurück")
    public void test_13(){
        UserEntity userEntity = UserEntity.create("pass1","user1",UUID.randomUUID());
        UserEntity userEntity1 = UserEntity.create("pass2","user2",UUID.randomUUID());
        UserEntity userEntity2 = UserEntity.create("pass3","user3",UUID.randomUUID());
        UserEntity userEntity3 = UserEntity.create("pass4","user4",UUID.randomUUID());
        UserEntity userEntity4 = UserEntity.create("pass5","user5",UUID.randomUUID());

        userRepo.saveUser(userEntity);
        userRepo.saveUser(userEntity1);
        userRepo.saveUser(userEntity2);
        userRepo.saveUser(userEntity3);
        userRepo.saveUser(userEntity4);

        UserDetails userDetails = userRepo.loadUserByUsername("user5");

        assertThat(userDetails.getUsername()).isEqualTo("user5");
        assertThat(userDetails.getPassword()).isEqualTo("pass5");
        assertThat(userDetails.getAuthorities()).hasSize(1);
    }

    @Sql({"classpath:sql-files/example_data2.sql"})
    @Test
    @DisplayName("Sucht nach dem Usernamen user6, dieser ist aber nicht vorhanden")
    public void test_14(){
        UserEntity userEntity = UserEntity.create("pass1","user1",UUID.randomUUID());
        UserEntity userEntity1 = UserEntity.create("pass2","user2",UUID.randomUUID());
        UserEntity userEntity2 = UserEntity.create("pass3","user3",UUID.randomUUID());
        UserEntity userEntity3 = UserEntity.create("pass4","user4",UUID.randomUUID());
        UserEntity userEntity4 = UserEntity.create("pass5","user5",UUID.randomUUID());

        userRepo.saveUser(userEntity);
        userRepo.saveUser(userEntity1);
        userRepo.saveUser(userEntity2);
        userRepo.saveUser(userEntity3);
        userRepo.saveUser(userEntity4);

        UserDetails userDetails = userRepo.loadUserByUsername("user7");

        assertThat(userDetails).isNull();
    }
}
