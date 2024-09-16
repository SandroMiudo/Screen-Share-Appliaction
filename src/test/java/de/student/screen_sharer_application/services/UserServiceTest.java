package de.student.screen_sharer_application.services;

import de.student.screen_sharer_application.user.UserEntity;
import de.student.screen_sharer_application.util.UUIDGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    IUserRepo iUserRepo = mock(IUserRepo.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    UUIDGenerator generator = mock(UUIDGenerator.class);
    UserService userService = new UserService(iUserRepo,passwordEncoder, generator);

    @Test
    @DisplayName("Falls der Name nicht [a-zA-Z0-9] enthält wird nichts gemacht")
    public void test_1(){
        UUID userID = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();

        UserFriendData data = new UserFriendData(userID,friendID,"-abc-");

        UserEntity userEntity = UserEntity.load("pass", "user", userID, new ArrayList<>());
        when(iUserRepo.getUser(userID)).thenReturn(userEntity);
        when(iUserRepo.getUser(friendID)).thenReturn(
                UserEntity.load("pass2","user2",friendID,new ArrayList<>()));
        userService.addFriend(data);

        verify(iUserRepo,never()).updateUserDetails(userEntity);
    }

    @Test
    @DisplayName("Falls der Name nicht [a-zA-Z0-9] enthält wird nichts gemacht")
    public void test_2(){
        UUID userID = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();

        UserFriendData data = new UserFriendData(userID,friendID,"<script></script>");

        UserEntity userEntity = UserEntity.load("pass", "user", userID, new ArrayList<>());
        when(iUserRepo.getUser(userID)).thenReturn(userEntity);
        when(iUserRepo.getUser(friendID)).thenReturn(
                UserEntity.load("pass2","user2",friendID,new ArrayList<>()));
        userService.addFriend(data);

        verify(iUserRepo,never()).updateUserDetails(userEntity);
    }

    @Test
    @DisplayName("Falls der Name nur [a-zA-Z0-9] enthält wird der Freund hinzugefügt")
    public void test_3(){
        UUID userID = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();
        UserFriendData friendData = new UserFriendData(userID,friendID,"afriend");
        UserEntity userEntity = UserEntity.load("pass","username",userID,new ArrayList<>());
        when(iUserRepo.getUser(userID)).thenReturn(userEntity);
        when(iUserRepo.getUser(friendID)).thenReturn(userEntity);

        userService.addFriend(friendData);

        verify(iUserRepo,times(1)).updateUserDetails(userEntity);
    }

    @Test
    @DisplayName("Falls der Name nur [a-zA-Z0-9] enthält und wenn der Freund vorhanden ist , " +
            "wird der Freund nicht hinzugefügt")
    public void test_4(){
        UUID userID = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();
        UserFriendData friendData = new UserFriendData(userID,friendID,"afriend");
        UserEntity userEntity = UserEntity.load("pass","username",userID,List.of(
                new UserFriendData(userID,friendID,"friendname")
        ));

        when(iUserRepo.getUser(userID)).thenReturn(userEntity);
        when(iUserRepo.getUser(friendID)).thenReturn(any());

        userService.addFriend(friendData);

        verify(iUserRepo,times(0)).updateUserDetails(userEntity);
    }

    @Test
    @DisplayName("Ein Benutzer wird aus der Datenbank gelöscht, wenn dieser auch in der Freundesliste vorhanden ist")
    public void test_5(){
        UUID userID = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();
        UserEntity userEntity = UserEntity.load("pass","username",userID,List.of(
                new UserFriendData(userID,friendID,"friendname")
        ));

        when(iUserRepo.getUser(userID)).thenReturn(userEntity);

        userService.removeFriend(userID,friendID);

        verify(iUserRepo,times(1)).updateUserDetails(userEntity);
    }

    @Test
    @DisplayName("Ein Benutzer wird nicht aus der Datenbank gelöscht, " +
            "wenn dieser auch nicht in der Freundesliste vorhanden ist")
    public void test_6(){
        UUID userID = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();
        UserEntity userEntity = UserEntity.load("pass","username",userID,List.of(
                new UserFriendData(userID,UUID.randomUUID(),"friendname")
        ));

        when(iUserRepo.getUser(userID)).thenReturn(userEntity);

        userService.removeFriend(userID,friendID);

        verify(iUserRepo,times(0)).updateUserDetails(userEntity);
    }

    @Test
    @DisplayName("")
    public void test_7(){
        RegistrationData registrationData = new RegistrationData("user1","pass2","pass2");

        when(generator.generate()).thenReturn(UUID.randomUUID());

        userService.save(registrationData);

        verify(iUserRepo,times(1)).saveUser(any());
    }
}
