package de.student.screen_sharer_application.sys;

import de.student.screen_sharer_application.dev.config.TestConfiguration;
import de.student.screen_sharer_application.statistics.Statistic;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class StatisticsRepoTest {

    @Autowired
    StatisticsRepo statisticsRepo;

    @Autowired
    TestConfiguration configuration;

    @Sql({"classpath:/sql-files/example_data_statistics.sql"})
    @Test
    @DisplayName("Eine Statistic am Montag wird hinzugefügt")
    public void test_1(){
        Statistic statistic = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,30,0));
        int update = statisticsRepo.update(statistic);

        assertThat(update).isEqualTo(1);
    }

    @Sql({"classpath:/sql-files/example_data_statistics.sql"})
    @Test
    @DisplayName("Drei Statistics am Montag werden hinzugefügt")
    public void test_2(){
        Statistic statistic = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,30,0));
        Statistic statistic2 = new Statistic(DayOfWeek.TUESDAY,
                LocalDateTime.of(2010,10,10,10,30,0));
        Statistic statistic3 = new Statistic(DayOfWeek.WEDNESDAY,
                LocalDateTime.of(2010,10,10,10,30,0));

        int gen = 0;
        gen += statisticsRepo.update(statistic);
        gen += statisticsRepo.update(statistic2);
        gen += statisticsRepo.update(statistic3);

        assertThat(gen).isEqualTo(3);
    }

    @Sql({"classpath:/sql-files/example_data_statistics.sql"})
    @Test
    @DisplayName("Es gibt 168 (7*24) Values in der DB woraus sich die Statistiken ablesen lassen")
    public void test_3(){
        List<Statistic> all = statisticsRepo.getAll();
        assertThat(all.size()).isEqualTo(7*24);
    }

    @Sql({"classpath:/sql-files/example_data_statistics.sql"})
    @Test
    @DisplayName("Zufällige Statistic hat am Anfang den RoomCounter auf 0")
    public void test_4(){
        Statistic stat = statisticsRepo.getStat(DayOfWeek.MONDAY, LocalTime.of(10, 30, 0));
        assertThat(stat.getRoomCount()).isEqualTo(0);
    }

    @Sql({"classpath:/sql-files/example_data_statistics.sql"})
    @Test
    @DisplayName("Statistic wird hinzugefügt und somit der roomcounter erhöht")
    public void test_5(){
        Statistic statistic = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,30,0));
        statisticsRepo.update(statistic);
        Statistic stat = statisticsRepo.getStat(DayOfWeek.MONDAY, LocalTime.of(10, 30, 0));
        assertThat(stat.getRoomCount()).isEqualTo(1);
    }

    @Sql({"classpath:/sql-files/example_data_statistics.sql"})
    @Test
    @DisplayName("2 Statistiken aus dem selben Zeit Interval")
    public void test_6(){
        Statistic statistic = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,30,0));
        Statistic statistic2 = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,59,59));
        statisticsRepo.update(statistic);
        statisticsRepo.update(statistic2);
        Statistic stat = statisticsRepo.getStat(DayOfWeek.MONDAY, LocalTime.of(10, 30, 0));
        assertThat(stat.getRoomCount()).isEqualTo(2);
    }

    @Sql({"classpath:/sql-files/example_data_statistics.sql"})
    @Test
    @DisplayName("4 Statistiken aus dem selben Zeit Interval")
    public void test_7(){
        Statistic statistic = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,30,0));
        Statistic statistic2 = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,59,59));
        Statistic statistic3 = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,0,0));
        Statistic statistic4 = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,0,30));
        statisticsRepo.update(statistic);
        statisticsRepo.update(statistic2);
        statisticsRepo.update(statistic3);
        statisticsRepo.update(statistic4);
        Statistic stat = statisticsRepo.getStat(DayOfWeek.MONDAY, LocalTime.of(10, 30, 0));
        assertThat(stat.getRoomCount()).isEqualTo(4);
    }

    @Sql({"classpath:/sql-files/example_data_statistics.sql"})
    @Test
    @DisplayName("4 Statistiken aus unterschiedlichen Zeit Intervalen")
    public void test_8(){
        Statistic statistic = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,30,0));
        Statistic statistic2 = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,9,59,59));
        Statistic statistic3 = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,17,0,0));
        Statistic statistic4 = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,20,0,30));
        statisticsRepo.update(statistic);
        statisticsRepo.update(statistic2);
        statisticsRepo.update(statistic3);
        statisticsRepo.update(statistic4);
        Statistic stat = statisticsRepo.getStat(DayOfWeek.MONDAY, LocalTime.of(10, 30, 0));
        Statistic stat1 = statisticsRepo.getStat(DayOfWeek.MONDAY, LocalTime.of(9, 30, 0));
        Statistic stat2 = statisticsRepo.getStat(DayOfWeek.MONDAY, LocalTime.of(17, 30, 0));
        Statistic stat3 = statisticsRepo.getStat(DayOfWeek.MONDAY, LocalTime.of(20, 30, 0));
        assertThat(stat.getRoomCount()).isEqualTo(1);
        assertThat(stat1.getRoomCount()).isEqualTo(1);
        assertThat(stat2.getRoomCount()).isEqualTo(1);
        assertThat(stat3.getRoomCount()).isEqualTo(1);
    }

    @Sql({"classpath:/sql-files/example_data_statistics.sql"})
    @Test
    @DisplayName("4 Statistiken aus unterschiedlichen Tagen")
    public void test_9(){
        Statistic statistic = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,30,0));
        Statistic statistic2 = new Statistic(DayOfWeek.TUESDAY,
                LocalDateTime.of(2010,10,10,10,59,59));
        Statistic statistic3 = new Statistic(DayOfWeek.FRIDAY,
                LocalDateTime.of(2010,10,10,10,0,0));
        Statistic statistic4 = new Statistic(DayOfWeek.SATURDAY,
                LocalDateTime.of(2010,10,10,10,0,30));
        statisticsRepo.update(statistic);
        statisticsRepo.update(statistic2);
        statisticsRepo.update(statistic3);
        statisticsRepo.update(statistic4);
        Statistic stat = statisticsRepo.getStat(DayOfWeek.MONDAY, LocalTime.of(10, 30, 0));
        Statistic stat2 = statisticsRepo.getStat(DayOfWeek.TUESDAY, LocalTime.of(10, 30, 0));
        Statistic stat3 = statisticsRepo.getStat(DayOfWeek.FRIDAY, LocalTime.of(10, 30, 0));
        Statistic stat4 = statisticsRepo.getStat(DayOfWeek.SATURDAY, LocalTime.of(10, 30, 0));
        assertThat(stat.getRoomCount()).isEqualTo(1);
        assertThat(stat2.getRoomCount()).isEqualTo(1);
        assertThat(stat3.getRoomCount()).isEqualTo(1);
        assertThat(stat4.getRoomCount()).isEqualTo(1);
    }

    @Sql({"classpath:/sql-files/example_data_statistics.sql"})
    @Test
    @DisplayName("Alle Room-Statistiken werden reset")
    public void test_10(){
        Statistic statistic = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,30,0));
        Statistic statistic2 = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,59,59));
        Statistic statistic3 = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,0,0));
        Statistic statistic4 = new Statistic(DayOfWeek.MONDAY,
                LocalDateTime.of(2010,10,10,10,0,30));
        statisticsRepo.update(statistic);
        statisticsRepo.update(statistic2);
        statisticsRepo.update(statistic3);
        statisticsRepo.update(statistic4);

        statisticsRepo.reset();
        Statistic stat = statisticsRepo.getStat(DayOfWeek.MONDAY, LocalTime.of(10, 30, 0));
        assertThat(stat.getRoomCount()).isEqualTo(0);
    }
}
