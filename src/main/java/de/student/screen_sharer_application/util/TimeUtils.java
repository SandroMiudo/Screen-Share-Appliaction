package de.student.screen_sharer_application.util;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class TimeUtils {
    public LocalDateTime create(){
        return LocalDateTime.now();
    }

    public DayOfWeek dayOfWeek(){
        return LocalDateTime.now().getDayOfWeek();
    }
}
