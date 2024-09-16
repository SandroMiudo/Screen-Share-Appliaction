package de.student.screen_sharer_application.sys;

import de.student.screen_sharer_application.services.InviteRoomData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class InviteRepoTest {

    @Autowired
    InviteRepo inviteRepo;

    @Test
    @DisplayName("Alle Einladungen eines Users werden angezeigt")
    @Sql({"classpath:sql-files/example_data_invite.sql"})
    public void test_1(){
        UUID userID = UUID.fromString("d5c8e9a2-a2b8-45ed-86ca-6bfa309cead8");
        UUID roomID = UUID.fromString("0e9508ae-8f4a-44b2-b77f-dc086bb6604e");
        UUID roomID2 = UUID.fromString("544a958c-e48e-4151-993c-4983cf9d285b");
        UUID roomID3 = UUID.fromString("3954d816-69c2-434a-b558-e5921e3b532d");
        List<InviteRoomData> invites = inviteRepo.getInvites(userID);

        assertThat(invites).size().isEqualTo(3);

        assertThat(invites.get(0).roomID()).isEqualTo(roomID);
        assertThat(invites.get(1).roomID()).isEqualTo(roomID2);
        assertThat(invites.get(2).roomID()).isEqualTo(roomID3);
    }

    @Test
    @DisplayName("Falls ein Room-Invite expired ist wird dieser gelöscht und bei erneuter Anfrage " +
            "nicht mehr mit zurückgegeben")
    @Sql({"classpath:sql-files/example_data_invite.sql"})
    public void test_2(){
        LocalDateTime time = LocalDateTime.of(2010,1,1,10,15,30);
        UUID userID = UUID.fromString("d5c8e9a2-a2b8-45ed-86ca-6bfa309cead8");
        UUID roomID = UUID.fromString("0e9508ae-8f4a-44b2-b77f-dc086bb6604e");
        UUID roomID2 = UUID.fromString("544a958c-e48e-4151-993c-4983cf9d285b");
        UUID roomID3 = UUID.fromString("3954d816-69c2-434a-b558-e5921e3b532d");
        inviteRepo.removeInvites(time);

        List<InviteRoomData> invites = inviteRepo.getInvites(userID);
        InviteRoomData in = new InviteRoomData(roomID,userID);

        assertThat(invites).size().isEqualTo(2);
        assertThat(invites).doesNotContain(in);
        assertThat(invites.get(0).roomID()).isEqualTo(roomID2);
        assertThat(invites.get(1).roomID()).isEqualTo(roomID3);
    }
}
