package de.student.screen_sharer_application.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MessageServiceTest {
    IMessageRepo iMessageRepo = mock(IMessageRepo.class);
    RoomService roomService = mock(RoomService.class);
    MessageService messageService = new MessageService(iMessageRepo,roomService);

    @Test
    @DisplayName("Eine Message wird in der db gespeichert")
    public void test_1(){
        UUID userID = UUID.randomUUID();
        UUID roomID = UUID.randomUUID();
        RoomTransferData roomTransferData = new RoomTransferData(userID,"sandro");
        MessageData messageData = new MessageData("abc", LocalDateTime.now(), userID,roomID
                ,null,"");
        when(roomService.getUserRoomData(roomID,userID)).thenReturn(roomTransferData);
        messageService.send(messageData);
        verify(iMessageRepo,times(1)).send(messageData,roomTransferData);
    }

    @Test
    @DisplayName("Messages die nach 2010-10-10-05-20 werden gefiltert")
    public void test_2(){
        LocalDateTime t1 = LocalDateTime.of(2010,10,10,5,20);
        LocalDateTime t2 = LocalDateTime.of(2010,10,10,5,22);
        LocalDateTime t3 = LocalDateTime.of(2010,10,10,4,20);
        LocalDateTime t4 = LocalDateTime.of(2010,10,10,5,19);
        LocalDateTime t5 = LocalDateTime.of(2010,10,10,8,5);
        MessageData m1 = new MessageData("message1", t2, UUID.randomUUID(), UUID.randomUUID(),null,"");
        MessageData m2 = new MessageData("message4", t5, UUID.randomUUID(), UUID.randomUUID(),null,"");
        MessageDataFilter filter = new MessageDataFilter(UUID.randomUUID(),t1);
        when(iMessageRepo.getAllMessagesOfRoom(filter.roomID())).thenReturn(List.of(
                m1,m2,
                new MessageData("message2",t3,UUID.randomUUID(),UUID.randomUUID(),null,""),
                new MessageData("message3",t4,UUID.randomUUID(),UUID.randomUUID(),null,"")
        ));
        List<MessageData> messageData = messageService.retrieveMessagesForUser(filter);
        assertThat(messageData).contains(m1,m2);

    }
}
