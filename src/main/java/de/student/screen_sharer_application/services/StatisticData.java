package de.student.screen_sharer_application.services;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class StatisticData{
    private final DayOfWeek day;
    private final LocalDateTime time;

    public StatisticData(LocalDateTime time) {
        this.time = time;
        day = time.getDayOfWeek();
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
