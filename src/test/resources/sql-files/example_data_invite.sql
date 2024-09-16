drop table if exists Room_User_Data;
drop table if exists Room_Invite_Data;
drop table if exists Message_Data;
drop table if exists Room_Data;

create table Room_Data(
      roomID varchar(36) not null primary key,
      adminID varchar(36) not null,
      roomSize int,
      roomName varchar(15) not null
);

create table Room_Invite_Data(
     id int auto_increment primary key,
     friendID varchar(36) not null,
     roomID varchar(36) not null,
     timeCreation datetime,
     expire datetime,
     foreign key (roomID) references Room_Data(roomID)
);

create table Room_User_Data(
   id int auto_increment primary key,
   userID varchar(36) not null,
   roomNAME varchar(30) not null,
   roomID varchar(36) not null,
   foreign key(roomID) references Room_Data(roomID)
);

insert into Room_Data values ('0e9508ae-8f4a-44b2-b77f-dc086bb6604e','d00e9c4e-5b0b-4815-bd91-3335d9e8530f',4,'scaryroom');
insert into Room_Data values ('544a958c-e48e-4151-993c-4983cf9d285b','5e27653c-249c-4aad-a879-a98dfc319c97',3,'aroom');
insert into Room_Data values ('3954d816-69c2-434a-b558-e5921e3b532d','04c620a9-82ed-46f4-be9e-0fb907aafb51',2,'chicken');

insert into Room_Invite_Data (friendID,roomID,timeCreation,expire)
    values ('d5c8e9a2-a2b8-45ed-86ca-6bfa309cead8','0e9508ae-8f4a-44b2-b77f-dc086bb6604e','2010-01-01 10:00:01','2010-01-01 10:15:01');
insert into Room_Invite_Data (friendID,roomID,timeCreation,expire)
    values ('d5c8e9a2-a2b8-45ed-86ca-6bfa309cead8','544a958c-e48e-4151-993c-4983cf9d285b','2010-01-01 10:05:15','2010-01-01 10:20:15');
insert into Room_Invite_Data (friendID,roomID,timeCreation,expire)
    values ('d5c8e9a2-a2b8-45ed-86ca-6bfa309cead8','3954d816-69c2-434a-b558-e5921e3b532d','2010-01-01 10:10:59','2010-01-01 10:25:59');
