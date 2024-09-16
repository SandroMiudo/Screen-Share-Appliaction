package de.student.screen_sharer_application.services;

import de.student.screen_sharer_application.statistics.Statistic;

import java.util.List;

public interface IStatistics {
    public int update(Statistic statistic);
    public List<Statistic> getAll();
    public void reset();
}
