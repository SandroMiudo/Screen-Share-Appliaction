package de.student.screen_sharer_application.controller;

import de.student.screen_sharer_application.room.RoomEntity;
import de.student.screen_sharer_application.services.*;
import de.student.screen_sharer_application.util.TimeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.UUID;

@Controller
@Secured("ROLE_USER")
public class  WController {

    private final UserService userService;
    private final RoomService roomService;
    private final MessageService messageService;
    private final TimeUtils timeUtils;
    private final InviteService inviteService;
    private final RoomFilterService filterService;
    private final StatisticsService statisticsService;

    public WController(UserService userService, RoomService roomService, MessageService
            messageService, TimeUtils timeUtils, InviteService inviteService, RoomFilterService filterService,
                       StatisticsService statisticsService) {
        this.userService = userService;
        this.roomService = roomService;
        this.messageService = messageService;
        this.timeUtils = timeUtils;
        this.inviteService = inviteService;
        this.filterService = filterService;
        this.statisticsService = statisticsService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/register")
    public String register(RegistrationData registrationData){
        if(registrationData.hasErrors()){
            return "redirect:/login?error=true";
        }
        userService.save(registrationData);
        return "redirect:/login";
    }

    @GetMapping("/index/main")
    public String index(Principal principal, Model model){
        UserData userData = userService.getUserByName(principal.getName());
        List<UserFriendData> friends = userService.getAllFriends(userData.userID());
        List<InviteRoomData> invites = inviteService.retrieveInvites(userData.userID());
        List<RoomInfo> fullRooms = filterService.filterByFullRooms();
        List<RoomInfo> notFullRooms = filterService.filterByNotFullRooms();
        List<RoomInfo> current10rooms = filterService.getRandom10Rooms();
        List<RoomInfo> current10joinrooms = filterService.getRandom10JoinableRooms();
        List<RoomInfo> allRooms = roomService.getAllRooms();
        Map<DayOfWeek, Map<LocalTime, Integer>> s = statisticsService.getStatistics();
        SortedMap<LocalTime, Integer> statistics = statisticsService.getStatisticsSorted(timeUtils.dayOfWeek(),s);
        model.addAttribute("day",timeUtils.dayOfWeek());
        model.addAttribute("statistics",statistics);
        model.addAttribute("currentroomsize",allRooms.size());
        model.addAttribute("allrooms",allRooms);
        model.addAttribute("joinableroomsize",notFullRooms.size());
        model.addAttribute("joinrooms",current10joinrooms);
        model.addAttribute("currentrooms",current10rooms);
        model.addAttribute("friends",friends);
        model.addAttribute("friendcount",friends.size());
        model.addAttribute("invites",invites);
        model.addAttribute("invitecount",invites.size());
        model.addAttribute("fullRooms",fullRooms);
        model.addAttribute("notFullRooms",notFullRooms);
        model.addAttribute("username",userData.username());
        model.addAttribute("userid",userData.userID());
        return "main-site";
    }

    private void saveStat(StatisticData statisticData){
        statisticsService.updateStatistic(statisticData);
    }

    @PostMapping("/index/room/create")
    public String createRoom(RoomData roomData){
       if(roomData.hasErrors()) return "redirect:/index/main";
        UUID room = roomService.createRoom(roomData);
        saveStat(new StatisticData(timeUtils.create()));
        return "redirect:/index/room/session/" + room.toString();
    }

    @PostMapping("/index/friend/remove")
    public ResponseEntity<ResponseMessage> removeFriend(@RequestBody UserFriendData friendData){
        if(friendData.hasErrors()){
            FailMessage failMessage = new FailMessage();
            failMessage.rawResponseMessage(new Messages.FriendRemove(friendData.friendname()));
            return new ResponseEntity<>(failMessage,HttpStatus.ACCEPTED);
        }
        userService.removeFriend(friendData.userid(),friendData.friendid());
        SuccessMessage successMessage = new SuccessMessage();
        successMessage.rawResponseMessage(new Messages.FriendRemove(friendData.friendname()));
        return new ResponseEntity<>(successMessage,HttpStatus.ACCEPTED);
    }

    @PostMapping("/index/friend/add")
    public ResponseEntity<ResponseMessage> addFriend(@RequestBody UserFriendData friendData){
        if(friendData.hasErrors()){
            FailMessage failMessage = new FailMessage();
            failMessage.rawResponseMessage(new Messages.FriendAdd(friendData.friendname()));
            return new ResponseEntity<>(failMessage,HttpStatus.ACCEPTED);
        }
        userService.addFriend(friendData);
        SuccessMessage successMessage = new SuccessMessage();
        successMessage.rawResponseMessage(new Messages.FriendAdd(friendData.friendname()));
        return new ResponseEntity<>(successMessage,HttpStatus.ACCEPTED);
    }

    @PostMapping("/index/room/join")
    public String joinRoom(JoiningRoomData joiningRoomData){
        if(joiningRoomData.hasErrors()){
            return "redirect:/index/login";
        }
        RoomStatus roomStatus = roomService.joinRoom(joiningRoomData);
        if(roomStatus.compareTo(RoomStatus.MAX_CAP) == 0 || roomStatus.compareTo(RoomStatus.NOT_EXISTING) == 0){
            return "redirect:/index/login";
        }
        return "redirect:/index/room/session/"+joiningRoomData.roomCode().toString();
    }

    @PostMapping("/index/room/invite")
    public ResponseEntity<SuccessMessage> invite(@RequestBody InviteRoomData inviteRoomData){
        LocalDateTime currentTime = timeUtils.create();
        roomService.inviteToRoom(currentTime,currentTime.plusMinutes(15),inviteRoomData);
        SuccessMessage successMessage = new SuccessMessage();
        successMessage.rawResponseMessage(new Messages.InviteMessage(inviteRoomData.friendID()));
        return new ResponseEntity<>(successMessage,HttpStatus.ACCEPTED);
    }

    @PostMapping("/index/room/change/admin")
    public ResponseEntity<ResponseMessage> changeAdmin(@RequestBody AdminChangeData adminChangeData){
        UUID newAdmin = roomService.changeAdmin(adminChangeData);
        if(newAdmin == null){
            FailMessage failMessage = new FailMessage();
            failMessage.rawResponseMessage(new Messages.AdminMessage());
            return new ResponseEntity<>(failMessage,HttpStatus.NOT_FOUND);
        }
        SuccessMessage successMessage = new SuccessMessage();
        successMessage.rawResponseMessage(new Messages.AdminMessage());
        return new ResponseEntity<>(successMessage,HttpStatus.ACCEPTED);
    }

    @PostMapping("/index/invites")
    public ResponseEntity<List<InviteRoomData>> getInvites(@RequestBody UserRoomData userRoomData){
        List<InviteRoomData> invites = inviteService.retrieveInvites(userRoomData.userID());
        return new ResponseEntity<>(invites,HttpStatus.ACCEPTED);
    }

    @PostMapping("/index/friends")
    public ResponseEntity<List<UserFriendData>> getFriends(@RequestBody UserRoomData userRoomData){
        List<UserFriendData> friends = userService.getAllFriends(userRoomData.userID());
        return new ResponseEntity<>(friends,HttpStatus.ACCEPTED);
    }

    @PostMapping("/index/room/leave")
    public String leaveRoom(LeavingRoomData leavingRoomData){
        roomService.leaveRoom(leavingRoomData);
        return "redirect:/index/main";
    }

    @PostMapping("/index/room/remove")
    public ResponseEntity<ResponseMessage> removeUserFromRoom(@RequestBody RemoveUserData removeUserData){
        UserRoomData userData = roomService.getUser(removeUserData.removeUserID(), removeUserData.roomID());
        RemoveStatus removeStatus = roomService.removeUser(removeUserData);
        if(removeStatus.equals(RemoveStatus.NOT_ALLOWED)){
            FailMessage failMessage = new FailMessage();
            failMessage.rawResponseMessage(new Messages.RemoveMessage(userData.roomName()));
            return new ResponseEntity<>(failMessage,HttpStatus.NOT_FOUND);
        }
        SuccessMessage successMessage = new SuccessMessage();
        successMessage.rawResponseMessage(new Messages.RemoveMessage(userData.roomName()));
        return new ResponseEntity<>(successMessage,HttpStatus.ACCEPTED);
    }

    @ModelAttribute("user")
    public String getName(Principal principal){
        if(principal == null) return "";
        return principal.getName();
    }

    @GetMapping("/index/room/session/{room_id}")
    public String mainRoom(@PathVariable String room_id,@ModelAttribute("user") String name,Model model){
        UUID roomID;
        try {
            roomID = UUID.fromString(room_id);
        }catch (Exception e) {return "redirect:/index/main";}
        RoomEntity roomInfo = roomService.getRoomInfo(roomID);
        UserData userData = userService.getUserByName(name);
        if(userData == null) return "redirect:/index/main";
        List<UserFriendData> friends = userService.getAllFriends(userData.userID());
        model.addAttribute("uservalue",userData.userID().toString());
        model.addAttribute("friends",friends);
        model.addAttribute("roomsize",roomInfo.getRoomSize());
        model.addAttribute("roomname",roomInfo.getRoomName());
        model.addAttribute("roomusers",roomInfo.getUserIDs());
        model.addAttribute("roomcounter",roomInfo.getUserIDs().size());
        model.addAttribute("roomadmin",roomInfo.getAdminID().toString());
        model.addAttribute("roomid",roomInfo.getRoomID().toString());
        model.addAttribute("roomjointime", timeUtils.create());
        return "main-room-site";
    }

    @PostMapping("/index/room/add/message")
    public ResponseEntity<MessageData> sendMessage(@RequestBody MessageData messageData){
        if(messageData.hasErrors())return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        messageService.send(messageData);
        return new ResponseEntity<>(messageData,HttpStatus.ACCEPTED);
    }

    @PostMapping("/index/room/messages")
    public ResponseEntity<List<MessageData>> getMessages(@RequestBody MessageDataFilter messageFilter){
        if(messageFilter.hasErrors()) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        List<MessageData> messageData = messageService.retrieveMessagesForUser(messageFilter);
        return new ResponseEntity<>(messageData,HttpStatus.ACCEPTED);
    }

    @PostMapping("/index/room/users")
    public ResponseEntity<List<RoomTransferData>> getUsers(@RequestBody JSONHandler handler) {
        try{
            UUID rID = UUID.fromString(handler.roomID());
            RoomEntity room = roomService.getRoomInfo(rID);
            return new ResponseEntity<>(room.getUserIDs(),HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);}
    }
}

