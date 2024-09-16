package de.student.screen_sharer_application.services;

import de.student.screen_sharer_application.user.UserEntity;
import de.student.screen_sharer_application.util.UUIDGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService{

    private final IUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UUIDGenerator uuidGenerator;

    public UserService(IUserRepo userRepo, PasswordEncoder passwordEncoder, UUIDGenerator uuidGenerator) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.uuidGenerator = uuidGenerator;
    }

    public void save(RegistrationData registrationData){
        List<UserEntity> allUsers = userRepo.getAllUsers();
        boolean match = allUsers.stream().map(UserEntity::getUsername)
                .anyMatch(x -> x.equals(registrationData.username()));
        if(match) return;
        UserEntity userEntity = UserEntity.create(registrationData.password(),registrationData.username(),
                uuidGenerator.generate());
        userEntity.encodePassword(passwordEncoder);
        userRepo.saveUser(userEntity);
    }

    public void removeFriend(UUID userID, UUID friendID){
        UserEntity userEntity = userRepo.getUser(userID);
        boolean removed = userEntity.removeFriend(friendID);
        if(removed) userRepo.updateUserDetails(userEntity);
    }

    public UserData getUserByName(String username){
        return userRepo.getUserByName(username);
    }

    public List<UserFriendData> getAllFriends(UUID userID){
        UserEntity userEntity = userRepo.getUser(userID);
        return userEntity.getFriends();
    }

    public UserFriendData getFriend(UUID userID, UUID friendID){
        UserEntity userEntity = userRepo.getUser(userID);
        return userEntity.getFriend(friendID);
    }

    public void addFriend(UserFriendData friendData){
        UserEntity userEntity = userRepo.getUser(friendData.userid());
        UserEntity friend = userRepo.getUser(friendData.friendid());
        if(friend == null) return;
        boolean added = userEntity.addFriend(friendData.friendid(), friendData.friendname());
        if(added){
            userRepo.updateUserDetails(userEntity);
        }
    }
}
