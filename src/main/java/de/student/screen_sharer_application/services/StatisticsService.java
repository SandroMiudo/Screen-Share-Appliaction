package de.student.screen_sharer_application.services;

import com.sun.source.tree.Tree;
import de.student.screen_sharer_application.statistics.Statistic;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final IStatistics iStatistics;

    public StatisticsService(IStatistics iStatistics) {
        this.iStatistics = iStatistics;
    }

    public void updateStatistic(StatisticData statisticData){
        iStatistics.update(new Statistic(statisticData.getDay(),statisticData.getTime()));
    }

    public void resetStatistics(){
        iStatistics.reset();
    }

    public Map<DayOfWeek,Map<LocalTime,Integer>> getStatistics(){
        List<Statistic> statistics = iStatistics.getAll();
        return statistics.stream().map(Statistic::changeTimeToFlatHour)
                .collect(Collectors.groupingBy(Statistic::getDayOfWeek,
                        Collectors.groupingBy(Statistic::getLocalTime,
                                Collectors.summingInt(Statistic::getRoomCount))));
    }

    public SortedMap<LocalTime,Integer> getStatisticsSorted(DayOfWeek day, Map<DayOfWeek,Map<LocalTime,Integer>> m){
        Map<LocalTime, Integer> map = m.get(day);
        return new TreeMap<>(map);
    }
}
