drop table if exists Room_Invite_Data;

create table Room_Invite_Data(
    id int auto_increment primary key,
    friendID varchar(36) not null,
    roomID varchar(36) not null,
    timeCreation datetime not null,
    expire datetime not null,
    foreign key (roomID) references Room_Data(roomID)
);