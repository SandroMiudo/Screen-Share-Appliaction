package de.student.screen_sharer_application.sys;

import de.student.screen_sharer_application.services.InviteService;
import de.student.screen_sharer_application.services.StatisticsService;
import de.student.screen_sharer_application.util.TimeUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class Scheduler {

    private final InviteService inviteService;
    private final StatisticsService statisticsService;
    private final TimeUtils timeUtils;
    private LocalDateTime currentMonday;

    public Scheduler(InviteService inviteService, StatisticsService statisticsService,
                     TimeUtils timeUtils) {
        this.inviteService = inviteService;
        this.statisticsService = statisticsService;
        this.timeUtils = timeUtils;
    }

    @Scheduled(fixedRate = 1000)
    public void updateStatistics(){
        LocalDateTime currentDate = timeUtils.create();
        if(currentMonday == null && currentDate.getDayOfWeek().equals(DayOfWeek.MONDAY)){
            currentMonday = currentDate;
            statisticsService.resetStatistics();
        }
        else if(nextMonday(currentDate)){
            currentMonday = currentDate;
            statisticsService.resetStatistics();
        }
    }

    @Scheduled(fixedRate = 5000)
    public void update(){
        inviteService.removeExpiredInvites(timeUtils.create());
    }

    private boolean nextMonday(LocalDateTime currentDate){
        return currentMonday != null && currentDate.getDayOfWeek().equals(DayOfWeek.MONDAY)
                && currentDate.getDayOfYear() > currentMonday.getDayOfYear();
    }
}
