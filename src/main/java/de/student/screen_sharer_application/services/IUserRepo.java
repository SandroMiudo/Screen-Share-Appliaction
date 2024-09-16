package de.student.screen_sharer_application.services;

import de.student.screen_sharer_application.user.UserEntity;

import java.util.List;
import java.util.UUID;

public interface IUserRepo {

    public void saveUser(UserEntity userEntity);

    public UserEntity getUser(UUID userID);

    public UserData getUserByName(String username);

    public void updateUserDetails(UserEntity userEntity);

    public List<UserEntity> getAllUsers();
}
