package service;

import domain.Game;
import domain.Sport;

import java.util.List;

public interface GameService {
    boolean gameWithOlympicNumber1Exists(int olympicNumber1);
    void save(Game game);
}

