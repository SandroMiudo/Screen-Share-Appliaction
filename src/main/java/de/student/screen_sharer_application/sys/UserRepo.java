package de.student.screen_sharer_application.sys;

import de.student.screen_sharer_application.services.*;
import de.student.screen_sharer_application.user.UserEntity;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class UserRepo implements IUserRepo, UserDetailsService {

    record LoginData(String password, String username,String userID){}

    private final JdbcTemplate template;

    public UserRepo(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        template.update("INSERT INTO User_Data values(?,?,?)",
                ps -> {
                    ps.setString(1,userEntity.getUserID().toString());
                    ps.setString(2,userEntity.getPassword());
                    ps.setString(3,userEntity.getUsername());
                });
    }

    @Override
    public UserEntity getUser(UUID userID) {
        List<LoginData> user = template.query("select * from User_Data where userID = ?", ps -> ps.setString(1, userID.toString()),
                new DataClassRowMapper<>(LoginData.class));
        if(user.isEmpty()) return null;
        LoginData userData = user.get(0);
        List<UserFriendData> friends = template.query("select * from User_Friend_Data where userID = ?", 
                ps -> ps.setString(1, userID.toString()),
                new DataClassRowMapper<>(UserFriendData.class));
        return UserEntity.load(userData.password,userData.username,userID,friends);
    }

    @Override
    public UserData getUserByName(String username) {
        return template.query("select userID,username from User_Data where username = ?",ps -> {
            ps.setString(1,username);
        },new DataClassRowMapper<>(UserData.class)).get(0);
    }

    @Override
    public void updateUserDetails(UserEntity userEntity) {
        template.update("delete from User_Friend_Data where userID = ?",
                ps -> ps.setString(1,userEntity.getUserID().toString()));
        template.update("delete from User_Data where userID = ?",
                ps -> ps.setString(1,userEntity.getUserID().toString()));

        template.update("insert into User_Data values(?,?,?)",ps -> {
            ps.setString(1,userEntity.getUserID().toString());
            ps.setString(2, userEntity.getPassword());
            ps.setString(3, userEntity.getUsername());
        });
        userEntity.getFriends().forEach(x ->
                template.update("insert into User_Friend_Data (userID,friendID,friendName) values (?,?,?)",
                ps -> {
                    ps.setString(1,x.userid().toString());
                    ps.setString(2,x.friendid().toString());
                    ps.setString(3,x.friendname());
                }));
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return template.query("select * from User_Data", UserRepo::userMapping);
    }

    private static List<UserEntity> userMapping(ResultSet resultSet) throws SQLException {
        List<UserEntity> userEntities = new ArrayList<>();
        while (resultSet.next()){
            userEntities.add(UserEntity.create(resultSet.getString(2),resultSet.getString(3),
                    UUID.fromString(resultSet.getString(1))));
        }
        return userEntities;
    }

    public int getMaxRowsUserData(){
        return template.query("SELECT * from User_Data",new DataClassRowMapper<>(LoginData.class)).size();
    }

    public int getMaxRowsFriendData(){
        return template.query("SELECT userID,friendID,friendName from User_Friend_Data",
                new DataClassRowMapper<>(UserFriendData.class)).size();
    }

    private static LoginData mapLoginData(ResultSet rs) throws SQLException {
        if(rs.next()){
            return new LoginData(rs.getString(2),rs.getString(3),rs.getString(1));
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginData data = template.query("select * from User_Data where username = ?",
                ps -> ps.setString(1, username),
                UserRepo::mapLoginData);
        if(data == null) return null;
        Set<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("USER"));
        return new User(data.username,data.password,authorities);
    }
}
