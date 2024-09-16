drop table if exists Room_User_Data;
drop table if exists Message_Data;
drop table if exists Room_Invite_Data;
drop table if exists Room_Data;

create table Room_Data(
  roomID varchar(36) not null primary key,
  adminID varchar(36) not null,
  roomSize int,
  roomName varchar(15) not null
);

create table Room_User_Data(
   id int auto_increment primary key,
   userID varchar(36) not null,
   roomNAME varchar(30) not null,
   roomID varchar(36) not null,
   foreign key(roomID) references Room_Data(roomID)
);

create table Room_Invite_Data(
     id int auto_increment primary key,
     friendID varchar(36) not null,
     roomID varchar(36) not null,
     timeCreation datetime not null,
     expire datetime not null,
     foreign key (roomID) references Room_Data(roomID)
);

