package de.student.screen_sharer_application.sys;

import de.student.screen_sharer_application.services.IStatistics;
import de.student.screen_sharer_application.statistics.Statistic;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class StatisticsRepo implements IStatistics {

    private final JdbcTemplate template;

    public StatisticsRepo(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public int update(Statistic statistic) {
        return template.update("update Statistics_Data set roomCount = roomCount + 1 where day = ? and " +
                "? between timeFrom AND timeTo", ps -> {
            ps.setString(1, statistic.getDayOfWeek().name());
            ps.setTime(2, Time.valueOf(statistic.getTime().toLocalTime()));
        });
    }

    public Statistic getStat(DayOfWeek day, LocalTime localTime){
        return template.query("select day,timeFrom,timeto,roomCount from Statistics_Data where day = ? and " +
                "? between timeFrom AND timeTo",ps -> {
            ps.setString(1,day.name());
            ps.setTime(2,Time.valueOf(localTime));
        },(rs,i) -> new Statistic(DayOfWeek.valueOf(rs.getString(1)),
                    rs.getTime(2).toLocalTime(),rs.getTime(3).toLocalTime(),
                rs.getInt(4))).get(0);
    }

    @Override
    public List<Statistic> getAll() {
        return template.query("select day,timeFrom,timeTo,roomCount from Statistics_Data",(rs,i) ->
                new Statistic(DayOfWeek.valueOf(rs.getString(1)),
                        rs.getTime(2).toLocalTime(),rs.getTime(3).toLocalTime(),
                        rs.getInt(4)));
    }

    @Override
    public void reset() {
        template.update("update Statistics_Data set roomCount = 0");
    }
}
