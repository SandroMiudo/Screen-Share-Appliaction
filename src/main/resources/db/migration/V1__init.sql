drop table if exists User_Friend_Data;
drop table if exists User_Data;
drop table if exists Room_User_Data;
drop table if exists Message_Data;
drop table if exists Room_Data;

create table User_Data(
    userID varchar(36) not null primary key,
    password varchar(100) not null,
    username varchar(36) not null
);

create table User_Friend_Data(
    id int auto_increment primary key,
    userID varchar(36) not null,
    friendID varchar(36) not null,
    friendName varchar(30) not null,
    foreign key(userID) references User_Data(userID)
);

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

create table Message_Data(
    rawmessage text not null,
    sendtime datetime not null,
    userID varchar(36) not null,
    roomID varchar(36) not null,
    messageID int auto_increment primary key,
    roomName varchar(36) not null,
    foreign key(roomID) references Room_Data(roomID)
);