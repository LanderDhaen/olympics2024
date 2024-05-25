package service;

import domain.Spectator;
import domain.Sport;

import java.util.List;
import java.util.Map;

public interface SportService {
    List<Sport> findAllSports();
    Sport findByName(String name);
    Map<Long, Integer> calculateTicketAmounts(Sport sport, Spectator spectator);
}
