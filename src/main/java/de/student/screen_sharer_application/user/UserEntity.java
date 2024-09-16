package de.student.screen_sharer_application.user;

import de.student.screen_sharer_application.services.UserFriendData;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

public class UserEntity {
    private final UUID userID;
    private String password;
    private final String username;
    private final List<UserFriend> friends = new ArrayList<>();

    private UserEntity(String password, String username, UUID userID){
        this.password = password;
        this.username = username;
        this.userID = userID;
    }

    private UserEntity(String password, String username, UUID userID,List<UserFriend> friends){
        this.password = password;
        this.username = username;
        this.userID = userID;
        this.friends.addAll(friends);
    }

    public static UserEntity create(String password, String username, UUID userID){
        return new UserEntity(password,username,userID);
    }

    public static UserEntity load(String password, String username, UUID userID, List<UserFriendData> friends){
        return new UserEntity(password,username,userID,mapDTO(friends));
    }

    private static List<UserFriend> mapDTO(List<UserFriendData> friends){
        return friends.stream().map(x -> new UserFriend(x.friendid(),x.friendname())).toList();
    }

    private List<UserFriendData> mapToDTO(){
        return friends.stream().map(x -> new UserFriendData(userID,x.userID(),x.userName())).toList();
    }

    public List<UserFriendData> getFriends() {
        return mapToDTO();
    }

    public boolean addFriend(UUID friendID, String username){
        boolean noneMatch = friends.stream().noneMatch(x -> x.userID().equals(friendID));
        if(noneMatch && !Pattern.compile("[^a-zA-Z0-9]").matcher(username).find()) {
            friends.add(new UserFriend(friendID,username));
            return true;
        }
        return false;
    }

    public boolean removeFriend(UUID friendID){
        return friends.removeIf(x -> x.userID().equals(friendID));
    }

    public UserFriendData getFriend(UUID friendID){
        Optional<UserFriend> optionalUserFriend = friends.stream().filter(x -> x.userID().equals(friendID)).findAny();
        return optionalUserFriend.map(userFriend ->
                new UserFriendData(userID, userFriend.userID(), userFriend.userName())).orElse(null);
    }

    public void encodePassword(PasswordEncoder encoder){
        password = encoder.encode(password);
    }

    public UUID getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
