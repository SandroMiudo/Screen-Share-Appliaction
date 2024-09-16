package de.student.screen_sharer_application.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final IMessageRepo iMessageRepo;
    private final RoomService roomService;

    public MessageService(IMessageRepo iMessageRepo, RoomService roomService) {
        this.iMessageRepo = iMessageRepo;
        this.roomService = roomService;
    }

    public void send(MessageData messageData){
        RoomTransferData userRoomData = roomService.getUserRoomData(messageData.roomID(), messageData.userID());
        iMessageRepo.send(messageData,userRoomData);
    }

    public List<MessageData> retrieveMessagesForUser(MessageDataFilter messageDataFilter){
        List<MessageData> allMessagesOfRoom = iMessageRepo.getAllMessagesOfRoom(messageDataFilter.roomID());
        return allMessagesOfRoom.stream().filter(x -> x.sendtime().isAfter(messageDataFilter.time()))
                .sorted((o1,o2) -> {
                    if(o1.sendtime().isAfter(o2.sendtime())) return 1;
                    else if(o1.sendtime().isEqual(o2.sendtime())) return 0;
                    else return 0;
                }).toList();
    }
}
