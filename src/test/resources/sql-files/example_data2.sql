drop table if exists User_Friend_Data;
drop table if exists User_Data;

create table User_Data(
  userID varchar(36) not null primary key,
  password varchar not null,
  username varchar not null
);

create table User_Friend_Data(
     id int auto_increment primary key,
     userID varchar(36) not null,
     friendID varchar(36) not null,
     friendName varchar(30) not null,
     foreign key(userID) references User_Data(userID)
);