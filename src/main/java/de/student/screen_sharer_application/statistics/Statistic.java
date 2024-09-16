package de.student.screen_sharer_application.statistics;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Statistic{
    private final DayOfWeek dayOfWeek;
    private LocalDateTime time;
    private LocalTime from;
    private LocalTime to;
    private Integer roomCount;

    public Statistic(DayOfWeek dayOfWeek, LocalDateTime time) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
    }

    public Statistic(DayOfWeek dayOfWeek, LocalTime from,LocalTime to,
                     Integer roomCount) {
        this.dayOfWeek = dayOfWeek;
        this.from = from;
        this.to = to;
        this.roomCount = roomCount;
    }

    public Statistic changeTimeToFlatHour(){
        LocalTime t1 = from.withMinute(0).withSecond(0);
        LocalTime t2 = to.withMinute(0).withSecond(0);

        return new Statistic(dayOfWeek,t1,t2,roomCount);
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public LocalTime getLocalTime() {
        return from;
    }

    public Integer getRoomCount() {
        return roomCount;
    }
}
