drop table if exists Statistics_Data;

create table Statistics_Data(
                                id int primary key auto_increment,
                                day enum('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY'),
                                timeFrom time not null,
                                timeTo time not null,
                                roomCount int not null
);

INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','00:00:00','00:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','01:00:00','01:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','02:00:00','02:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','03:00:00','03:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','04:00:00','04:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','05:00:00','05:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','06:00:00','06:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','07:00:00','07:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','08:00:00','08:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','09:00:00','09:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','10:00:00','10:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','11:00:00','11:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','12:00:00','12:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','13:00:00','13:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','14:00:00','14:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','15:00:00','15:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','16:00:00','16:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','17:00:00','17:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','18:00:00','18:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','19:00:00','19:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','20:00:00','20:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','21:00:00','21:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','22:00:00','22:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('MONDAY','23:00:00','23:59:59',0);

INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','00:00:00','00:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','01:00:00','01:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','02:00:00','02:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','03:00:00','03:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','04:00:00','04:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','05:00:00','05:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','06:00:00','06:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','07:00:00','07:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','08:00:00','08:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','09:00:00','09:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','10:00:00','10:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','11:00:00','11:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','12:00:00','12:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','13:00:00','13:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','14:00:00','14:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','15:00:00','15:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','16:00:00','16:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','17:00:00','17:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','18:00:00','18:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','19:00:00','19:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','20:00:00','20:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','21:00:00','21:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','22:00:00','22:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('TUESDAY','23:00:00','23:59:59',0);

INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','00:00:00','00:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','01:00:00','01:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','02:00:00','02:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','03:00:00','03:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','04:00:00','04:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','05:00:00','05:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','06:00:00','06:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','07:00:00','07:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','08:00:00','08:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','09:00:00','09:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','10:00:00','10:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','11:00:00','11:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','12:00:00','12:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','13:00:00','13:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','14:00:00','14:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','15:00:00','15:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','16:00:00','16:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','17:00:00','17:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','18:00:00','18:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','19:00:00','19:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','20:00:00','20:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','21:00:00','21:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','22:00:00','22:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('WEDNESDAY','23:00:00','23:59:59',0);

INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','00:00:00','00:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','01:00:00','01:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','02:00:00','02:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','03:00:00','03:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','04:00:00','04:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','05:00:00','05:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','06:00:00','06:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','07:00:00','07:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','08:00:00','08:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','09:00:00','09:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','10:00:00','10:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','11:00:00','11:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','12:00:00','12:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','13:00:00','13:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','14:00:00','14:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','15:00:00','15:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','16:00:00','16:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','17:00:00','17:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','18:00:00','18:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','19:00:00','19:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','20:00:00','20:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','21:00:00','21:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','22:00:00','22:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('THURSDAY','23:00:00','23:59:59',0);

INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','00:00:00','00:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','01:00:00','01:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','02:00:00','02:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','03:00:00','03:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','04:00:00','04:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','05:00:00','05:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','06:00:00','06:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','07:00:00','07:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','08:00:00','08:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','09:00:00','09:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','10:00:00','10:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','11:00:00','11:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','12:00:00','12:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','13:00:00','13:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','14:00:00','14:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','15:00:00','15:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','16:00:00','16:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','17:00:00','17:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','18:00:00','18:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','19:00:00','19:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','20:00:00','20:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','21:00:00','21:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','22:00:00','22:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('FRIDAY','23:00:00','23:59:59',0);

INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','00:00:00','00:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','01:00:00','01:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','02:00:00','02:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','03:00:00','03:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','04:00:00','04:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','05:00:00','05:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','06:00:00','06:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','07:00:00','07:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','08:00:00','08:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','09:00:00','09:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','10:00:00','10:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','11:00:00','11:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','12:00:00','12:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','13:00:00','13:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','14:00:00','14:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','15:00:00','15:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','16:00:00','16:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','17:00:00','17:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','18:00:00','18:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','19:00:00','19:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','20:00:00','20:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','21:00:00','21:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','22:00:00','22:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SATURDAY','23:00:00','23:59:59',0);

INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','00:00:00','00:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','01:00:00','01:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','02:00:00','02:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','03:00:00','03:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','04:00:00','04:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','05:00:00','05:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','06:00:00','06:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','07:00:00','07:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','08:00:00','08:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','09:00:00','09:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','10:00:00','10:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','11:00:00','11:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','12:00:00','12:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','13:00:00','13:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','14:00:00','14:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','15:00:00','15:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','16:00:00','16:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','17:00:00','17:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','18:00:00','18:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','19:00:00','19:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','20:00:00','20:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','21:00:00','21:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','22:00:00','22:59:59',0);
INSERT INTO Statistics_Data (day, timeFrom, timeTo, roomCount)
VALUES ('SUNDAY','23:00:00','23:59:59',0);

