package de.student.screen_sharer_application.controller;

import de.student.screen_sharer_application.room.RoomEntity;
import de.student.screen_sharer_application.services.*;
import de.student.screen_sharer_application.util.TimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import javax.json.Json;
import javax.servlet.http.Cookie;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WController.class)
@AutoConfigureMockMvc(addFilters = false)
public class WControllerTest{

    @Autowired
    MockMvc mockMvc;

    @MockBean
    InviteService inviteService;

    @MockBean
    UserService userService;

    @MockBean
    RoomService roomService;

    @MockBean
    MessageService messageService;

    @MockBean
    RoomFilterService filterService;

    @MockBean
    StatisticsService statisticsService;

    @MockBean
    Principal principal;

    @MockBean
    TimeUtils time;

    @Test
    @DisplayName("User gibt bei der Erzeugung des Raumes invalide Daten ein -> roomname = sc")
    public void test_11() throws Exception {
        UUID sessionID = UUID.randomUUID();
        UUID userID = UUID.randomUUID();
        mockMvc.perform(post("/index/room/create")
                        .param("roomname","sc")
                        .param("roomsize","2")
                        .param("adminuser",""+userID)
                        .param("sessionuser",""+sessionID)
                        .param("username","sandro")).andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("User gibt bei der Erzeugung des Raumes invalide Daten ein -> roomsize = 10")
    public void test_12() throws Exception {
        UUID sessionID = UUID.randomUUID();
        UUID userID = UUID.randomUUID();
        mockMvc.perform(post("/index/room/create")
                .param("roomname","scaryroom")
                .param("roomsize","10")
                .param("adminuser",userID.toString())
                .param("sessionuser",sessionID.toString())).andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Bei invaliden Daten wird ein neuer Freund nicht hinzugefügt")
    public void test_13() throws Exception {
        String content = Json.createObjectBuilder()
                        .add("userid",UUID.randomUUID().toString())
                                .add("friendid",UUID.randomUUID().toString())
                                        .add("friendname","sa").build().toString();

        mockMvc.perform(post("/index/friend/add")
                        .contentType(MediaType.APPLICATION_JSON).content(content))
               .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Falls invalider room eingegeben wird")
    public void test_14() throws Exception {
        MultiValueMap<String,String> m = new LinkedMultiValueMap<>();
        m.add("roomCode","abc");
        m.add("uservalue",UUID.randomUUID().toString());
        m.add("roomname","scaryroom");

        mockMvc.perform(post("/index/room/join")
                .params(m)).andExpect(status().is4xxClientError());

        verify(roomService,never()).joinRoom(any());
    }

    @Test
    @DisplayName("Falls valide Daten eingegeben werden, so wird dem Raum beigetreten")
    public void test_15() throws Exception {
        MultiValueMap<String,String> m = new LinkedMultiValueMap<>();
        UUID roomID = UUID.randomUUID();
        UUID userID = UUID.randomUUID();
        String roomname = "sandro";
        m.add("roomCode",roomID.toString());
        m.add("uservalue",userID.toString());
        m.add("roomname",roomname);

        when(roomService.joinRoom(new JoiningRoomData(roomID,userID,roomname))).thenReturn(RoomStatus.SUCCESS);

        mockMvc.perform(post("/index/room/join")
                .params(m)).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index/room/session/"+ roomID));

        verify(roomService,times(1)).joinRoom(any());
    }

    @Test
    @DisplayName("Falls valide Daten eingegeben werden,aber der Raum voll ist, so wird dem Raum nicht beigetreten")
    public void test_16() throws Exception {
        MultiValueMap<String,String> m = new LinkedMultiValueMap<>();
        UUID roomID = UUID.randomUUID();
        UUID userID = UUID.randomUUID();
        String roomname = "sandro";
        m.add("roomCode",roomID.toString());
        m.add("uservalue",userID.toString());
        m.add("roomname",roomname);

        when(roomService.joinRoom(new JoiningRoomData(roomID,userID,roomname))).thenReturn(RoomStatus.MAX_CAP);

        mockMvc.perform(post("/index/room/join")
                .params(m)).andExpect(status().is3xxRedirection());

        verify(roomService,times(1)).joinRoom(any());
    }

    @Test
    @DisplayName("Model wird mit den richtigen values befüllt für den admin-user aus dem raum")
    public void test_17() throws Exception {
        UUID roomID = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();
        UUID userID =  UUID.randomUUID();
        when(principal.getName()).thenReturn("sandro");
        UserData userData = new UserData("sandro",userID);
        when(userService.getUserByName("sandro")).thenReturn(userData);
        when(roomService.getRoomInfo(roomID)).thenReturn(RoomEntity.load("scaryroom",5,userID,
                List.of(new RoomTransferData(userID,"adminuser")),roomID));
        when(userService.getAllFriends(userID)).thenReturn(List.of(new UserFriendData(userID,friendID,"peter")));
        when(time.create()).thenReturn(LocalDateTime.of(2010,2,10,0,0,0));
        mockMvc.perform(get("/index/room/session/{room_id}", roomID.toString())
                        .flashAttr("user","sandro"))
                .andExpect(model().attribute("friends",List.of(new UserFriendData(userID,friendID,"peter"))))
                .andExpect(model().attribute("roomsize",5))
                .andExpect(model().attribute("roomname","scaryroom"))
                .andExpect(model().attribute("roomusers",List.of(new RoomTransferData(userID,"adminuser"))))
                .andExpect(model().attribute("roomadmin",userID.toString()))
                .andExpect(model().attribute("roomid",roomID.toString()))
                .andExpect(model().attribute("roomjointime",LocalDateTime.of(2010,2,10,0,0,0)))
                .andExpect(model().attribute("uservalue",userID.toString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Die Nachricht wird gespeichert und als Response zurückgegeben")
    public void test_20() throws Exception {
        UUID userID = UUID.randomUUID();
        UUID roomID = UUID.randomUUID();
        String json = Json.createObjectBuilder()
                .add("rawmessage", "this is a message")
                .add("sendtime", LocalDateTime.of(2010, 10, 20, 0, 0)
                        .toString())
                .add("userID", userID.toString())
                .add("roomID", roomID.toString())
                .add("messageID","1")
                .add("roomName","").build().toString();
        String content = mockMvc.perform(post("/index/room/add/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andReturn().getResponse().getContentAsString();
        assertThat(content).contains("this is a message");
        assertThat(content).contains(userID.toString());
        assertThat(content).contains(roomID.toString());
        assertThat(content).contains(LocalDateTime.of(2010,10,20,0,0).toString());

        verify(messageService,times(1)).send(any());
    }

    @Test
    @DisplayName("Die Nachricht wird nicht gespeichert, da invalide Daten mit reingegeben werden -> Response null")
    public void test_21() throws Exception {
        UUID userID = UUID.randomUUID();
        UUID roomID = UUID.randomUUID();
        String json = Json.createObjectBuilder()
                .add("rawmessage", "")
                .add("sendtime", LocalDateTime.of(2010, 10, 20, 0, 0)
                        .toString())
                .add("userID", userID.toString())
                .add("roomID", roomID.toString())
                .add("messageID","1")
                .add("roomName","").build().toString();
        mockMvc.perform(post("/index/room/add/message")
                .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Messages werden gefiltert und die gefilterterten als Response zurück gegeben")
    public void test_22() throws Exception {
        UUID roomID = UUID.randomUUID();
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        LocalDateTime f2 = LocalDateTime.of(2010,10,10,0,1);
        LocalDateTime f3 = LocalDateTime.of(2010,10,10,0,5);
        List<MessageData> messages = List.of(new MessageData("message1",f2,user1,roomID,1,""),
                new MessageData("message2",f3,user2,roomID,2,""));
        LocalDateTime localDateTime = LocalDateTime.of(2010,10,10,0,0);
        MessageDataFilter dataFilter = new MessageDataFilter(roomID,localDateTime);
        String json = Json.createObjectBuilder()
                        .add("roomID", roomID.toString())
                                .add("time", localDateTime.toString())
                                        .build().toString();
        when(messageService.retrieveMessagesForUser(dataFilter)).thenReturn(messages);
        String contentAsString = mockMvc.perform(post("/index/room/messages")
                .contentType(MediaType.APPLICATION_JSON).content(json)).andReturn().getResponse().getContentAsString();
        assertThat(contentAsString).contains(roomID.toString(),user1.toString(),user2.toString(),f2.toString(),
                f3.toString(),"message1","message2");
    }

    @Test
    @DisplayName("Bei einer validen Message wird diese als ResponseEntity zurück gegeben und zusätzlich in der " +
            "db gespeichert")
    public void test_23() throws Exception {
        LocalDateTime time = LocalDateTime.of(2010,10,10,10,10);
        UUID userID = UUID.randomUUID();
        UUID roomID = UUID.randomUUID();
        String json = Json.createObjectBuilder()
                .add("rawmessage", "hello")
                .add("sendtime", time.toString())
                .add("userID", userID.toString())
                .add("roomID", roomID.toString())
                .add("messageID", "1")
                .add("roomName","").build().toString();
        String contentAsString = mockMvc.perform(post("/index/room/add/message")
                .contentType(MediaType.APPLICATION_JSON).content(json)).andReturn().getResponse().getContentAsString();

        assertThat(contentAsString).contains(userID.toString(),roomID.toString(),time.toString(),"hello");

        verify(messageService,times(1)).send(any());
    }

    @Test
    @DisplayName("Falls eine invaldie Message eingegeben wird oder andere Fehler auftreten sollte so wird " +
            "not found zurück gegeben")
    public void test_24() throws Exception {
        LocalDateTime time = LocalDateTime.of(2010,10,10,10,10);
        UUID userID = UUID.randomUUID();
        UUID roomID = UUID.randomUUID();
        String json = Json.createObjectBuilder()
                .add("rawmessage", "")
                        .add("sendtime", time.toString())
                                .add("userID", userID.toString())
                                        .add("roomID", roomID.toString())
                                                .add("messageID", "1")
                                                        .add("roomName","").build().toString();

        mockMvc.perform(post("/index/room/add/message").contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Freund wird zum Raum eingeladen")
    public void test_25() throws Exception {
        UUID roomID = UUID.randomUUID();
        UUID friendID = UUID.randomUUID();

        String json = Json.createObjectBuilder()
                .add("roomID",roomID.toString())
                .add("friendID",friendID.toString()).build().toString();

        LocalDateTime c = LocalDateTime.now();
        when(time.create()).thenReturn(c);
        mockMvc.perform(post("/index/room/invite").contentType("application/json")
                .content(json)).andExpect(status().isAccepted());

        verify(roomService,times(1)).inviteToRoom(c,c.plusMinutes(15),
                new InviteRoomData(roomID,friendID));
    }

    @Test
    @DisplayName("Alle Invites des angefragten Nutzers werden zurückgegeben")
    public void test_26() throws Exception {
        UUID userID = UUID.randomUUID();
        UUID room1 = UUID.randomUUID();
        UUID room2 = UUID.randomUUID();
        String userName = "sandro";

        String json = Json.createObjectBuilder()
                .add("userID",userID.toString())
                .add("roomName",userName).build().toString();

        when(inviteService.retrieveInvites(userID)).thenReturn(List.of(
                new InviteRoomData(room1,userID),new InviteRoomData(room2,userID)));

        String contentAsString = mockMvc.perform(post("/index/invites").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn().getResponse().getContentAsString();
        assertThat(contentAsString).contains(room1.toString(),room2.toString());
    }
    @Test
    @DisplayName("Neuer admin wird zugelassen und als success message als Response gegeben")
    public void test_27() throws Exception {
        UUID roomID = UUID.randomUUID();
        UUID newAdminID = UUID.randomUUID();
        UUID currentAdminID = UUID.randomUUID();
        String json = Json.createObjectBuilder()
                .add("roomID",roomID.toString())
                .add("newAdmin",newAdminID.toString())
                .add("currentAdmin",currentAdminID.toString()).build().toString();
        AdminChangeData adminChangeData = new AdminChangeData(roomID,newAdminID,currentAdminID);
        when(roomService.changeAdmin(adminChangeData)).thenReturn(newAdminID);

        ResultActions result = mockMvc.perform(post("/index/room/change/admin").contentType(MediaType.APPLICATION_JSON)
                .content(json));
        result.andExpect(status().isAccepted());
        String contentAsString = result.andReturn().getResponse().getContentAsString();

        assertThat(contentAsString).contains("Adding a new admin was successful.");
    }

    @Test
    @DisplayName("Neuer admin wird nicht zugelassen und als failed message als Response gegeben")
    public void test_28() throws Exception {
        UUID roomID = UUID.randomUUID();
        UUID newAdminID = UUID.randomUUID();
        UUID currentAdminID = UUID.randomUUID();
        String json = Json.createObjectBuilder()
                .add("roomID",roomID.toString())
                .add("newAdmin",newAdminID.toString())
                .add("currentAdmin",currentAdminID.toString()).build().toString();
        AdminChangeData adminChangeData = new AdminChangeData(roomID,newAdminID,currentAdminID);
        when(roomService.changeAdmin(adminChangeData)).thenReturn(null);

        ResultActions result = mockMvc.perform(post("/index/room/change/admin").contentType(MediaType.APPLICATION_JSON)
                .content(json));
        result.andExpect(status().isNotFound());
        String contentAsString = result.andReturn().getResponse().getContentAsString();

        assertThat(contentAsString).contains("Adding a new admin failed.");
    }

    @Test
    @DisplayName("Ein User aus dem Raum wird aus dem Raum entfernt, wenn der Admin-User diese Anfrage stellt")
    public void test_29() throws Exception {
        UUID roomID = UUID.randomUUID();
        UUID removeUserID = UUID.randomUUID();
        UUID adminID = UUID.randomUUID();
        String json = Json.createObjectBuilder()
                .add("roomID",roomID.toString())
                .add("removeUserID",removeUserID.toString())
                .add("adminID",adminID.toString()).build().toString();
        RemoveUserData removeUserData = new RemoveUserData(removeUserID,adminID,roomID);
        when(roomService.removeUser(removeUserData)).thenReturn(RemoveStatus.ALLOWED);
        when(roomService.getUser(removeUserID,roomID)).thenReturn(new UserRoomData(removeUserID,"sandro"));
        ResultActions result = mockMvc.perform(post("/index/room/remove").contentType(MediaType.APPLICATION_JSON)
                .content(json));
        result.andExpect(status().isAccepted());
        String contentAsString = result.andReturn().getResponse().getContentAsString();

        assertThat(contentAsString).contains("Removing the user sandro was successful.");
    }

    @Test
    @DisplayName("Ein User aus dem Raum wird nicht aus dem Raum entfernt, wenn User keine Admin Berechtigung besitzt")
    public void test_30() throws Exception {
        UUID roomID = UUID.randomUUID();
        UUID removeUserID = UUID.randomUUID();
        UUID adminID = UUID.randomUUID();
        String json = Json.createObjectBuilder()
                .add("roomID",roomID.toString())
                .add("removeUserID",removeUserID.toString())
                .add("adminID",adminID.toString()).build().toString();
        RemoveUserData removeUserData = new RemoveUserData(removeUserID,adminID,roomID);
        when(roomService.removeUser(removeUserData)).thenReturn(RemoveStatus.NOT_ALLOWED);
        when(roomService.getUser(removeUserID,roomID)).thenReturn(new UserRoomData(removeUserID,"sandro"));
        ResultActions result = mockMvc.perform(post("/index/room/remove").contentType(MediaType.APPLICATION_JSON)
                .content(json));
        result.andExpect(status().isNotFound());
        String contentAsString = result.andReturn().getResponse().getContentAsString();

        assertThat(contentAsString).contains("Removing the user sandro failed.");
    }
}
