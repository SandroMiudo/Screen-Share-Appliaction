package de.student.screen_sharer_application.services;

import de.student.screen_sharer_application.statistics.Statistic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static java.time.DayOfWeek.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class StatisticsServiceTest {

    IStatistics iStatistics = mock(IStatistics.class);
    StatisticsService statisticsService = new StatisticsService(iStatistics);

    @Test
    @DisplayName("1 Statistik Update hat stattgefunden")
    public void test_1(){
        StatisticData statisticData = new StatisticData(LocalDateTime.now());
        statisticsService.updateStatistic(statisticData);

        verify(iStatistics,times(1)).update(any());
    }

    @Test
    @DisplayName("Reset")
    public void test_2(){
        statisticsService.resetStatistics();

        verify(iStatistics,times(1)).reset();
    }

    @Test
    @DisplayName("Die Statistiken werden für einen Tag richtig aufgeteilt")
    public void test_3(){
        when(iStatistics.getAll()).thenReturn(List.of(
                new Statistic(MONDAY, LocalTime.of(10,0,0),
                        LocalTime.of(10,59,59),3),
                new Statistic(MONDAY,LocalTime.of(20,0,0),
                        LocalTime.of(20,59,59),1),
                new Statistic(MONDAY,LocalTime.of(23,0,0),
                        LocalTime.of(23,59,59),10)
        ));

        Map<DayOfWeek, Map<LocalTime, Integer>> statistics = statisticsService.getStatistics();
        Map<LocalTime, Integer> m = statistics.get(MONDAY);

        assertThat(m.containsKey(LocalTime.of(10,0,0))).isTrue();
        assertThat(m.containsKey(LocalTime.of(20,0,0))).isTrue();
        assertThat(m.containsKey(LocalTime.of(23,0,0))).isTrue();

        assertThat(m.get(LocalTime.of(10,0,0))).isEqualTo(3);
        assertThat(m.get(LocalTime.of(20,0,0))).isEqualTo(1);
        assertThat(m.get(LocalTime.of(23,0,0))).isEqualTo(10);
    }

    @Test
    @DisplayName("Die Statistiken werden für unterschiedliche Tage richtig aufgeteilt")
    public void test_4(){
        when(iStatistics.getAll()).thenReturn(List.of(
                new Statistic(MONDAY, LocalTime.of(10,0,0),
                        LocalTime.of(10,59,59),3),
                new Statistic(MONDAY,LocalTime.of(20,0,0),
                        LocalTime.of(20,59,59),1),
                new Statistic(MONDAY,LocalTime.of(23,0,0),
                        LocalTime.of(23,59,59),10),
                new Statistic(TUESDAY,LocalTime.of(18,0,0),
                        LocalTime.of(18,59,59),24),
                new Statistic(TUESDAY,LocalTime.of(23,0,0),
                        LocalTime.of(23,59,59),5),
                new Statistic(SUNDAY,LocalTime.of(10,0,0),
                        LocalTime.of(10,59,59),50),
                new Statistic(SUNDAY,LocalTime.of(21,0,0),
                        LocalTime.of(21,59,59),100)
        ));

        Map<DayOfWeek, Map<LocalTime, Integer>> statistics = statisticsService.getStatistics();
        Map<LocalTime, Integer> m_monday = statistics.get(MONDAY);
        Map<LocalTime, Integer> m_tuesday = statistics.get(TUESDAY);
        Map<LocalTime, Integer> m_sunday = statistics.get(SUNDAY);

        assertThat(m_monday.containsKey(LocalTime.of(10,0,0))).isTrue();
        assertThat(m_monday.containsKey(LocalTime.of(20,0,0))).isTrue();
        assertThat(m_monday.containsKey(LocalTime.of(23,0,0))).isTrue();

        assertThat(m_tuesday.containsKey(LocalTime.of(18,0,0))).isTrue();
        assertThat(m_tuesday.containsKey(LocalTime.of(23,0,0))).isTrue();

        assertThat(m_sunday.containsKey(LocalTime.of(10,0,0))).isTrue();
        assertThat(m_sunday.containsKey(LocalTime.of(21,0,0))).isTrue();

        assertThat(m_monday.get(LocalTime.of(10,0,0))).isEqualTo(3);
        assertThat(m_monday.get(LocalTime.of(20,0,0))).isEqualTo(1);
        assertThat(m_monday.get(LocalTime.of(23,0,0))).isEqualTo(10);

        assertThat(m_tuesday.get(LocalTime.of(18,0,0))).isEqualTo(24);
        assertThat(m_tuesday.get(LocalTime.of(23,0,0))).isEqualTo(5);

        assertThat(m_sunday.get(LocalTime.of(10,0,0))).isEqualTo(50);
        assertThat(m_sunday.get(LocalTime.of(21,0,0))).isEqualTo(100);
    }
}
