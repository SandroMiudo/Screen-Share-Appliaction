drop table if exists User_Friend_Data;
drop table if exists User_Data;
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

INSERT INTO Room_Data
    values ('02185388-afd5-422f-8ea6-3edbaaa72e9c','c139f461-315b-403e-a77b-8fde52f4cefa',4,'scaryroom');
INSERT INTO Room_Data
    values ('7fdac1a0-5daa-4135-bde9-5b18fc654fe0','8e623926-4e8e-48bf-b381-561b198e46fd',3,'hunts');

INSERT INTO Room_User_Data (userID, roomNAME,roomID)
    values ('c139f461-315b-403e-a77b-8fde52f4cefa','sandro','02185388-afd5-422f-8ea6-3edbaaa72e9c');
INSERT INTO Room_User_Data (userID, roomNAME,roomID)
    values ('98ce75e6-61c4-46de-b135-2359a4bcd035','telmo','02185388-afd5-422f-8ea6-3edbaaa72e9c');
INSERT INTO Room_User_Data (userID, roomNAME,roomID)
    values ('16e1bae9-17f8-4d27-99c8-77bf893143ee','junior','02185388-afd5-422f-8ea6-3edbaaa72e9c');

INSERT INTO Room_User_Data (userID, roomNAME,roomID)
    values ('d42779e5-2e4d-47cd-8b47-0da5a48ec083','peter','7fdac1a0-5daa-4135-bde9-5b18fc654fe0');
INSERT INTO Room_User_Data (userID, roomNAME,roomID)
    values ('8e623926-4e8e-48bf-b381-561b198e46fd','sandra','7fdac1a0-5daa-4135-bde9-5b18fc654fe0');
INSERT INTO Room_User_Data (userID, roomNAME,roomID)
    values ('c139f461-315b-403e-a77b-8fde52f4cefa','josei','7fdac1a0-5daa-4135-bde9-5b18fc654fe0');

