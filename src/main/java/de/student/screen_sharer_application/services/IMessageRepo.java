package de.student.screen_sharer_application.services;

import java.util.List;
import java.util.UUID;

public interface IMessageRepo {
    public void send(MessageData messageData, RoomTransferData roomTransferData);
    public List<MessageData> getAllMessagesOfRoom(UUID roomID);
}
