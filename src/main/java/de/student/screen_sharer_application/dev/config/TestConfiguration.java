package de.student.screen_sharer_application.dev.config;

import de.student.screen_sharer_application.statistics.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Profile("test")
@Configuration
public class TestConfiguration {

    @Autowired
    JdbcTemplate jdbcTemplate;

}
