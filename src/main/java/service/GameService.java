package service;

import domain.Game;

import java.util.Set;

public interface GameService {
    boolean gameWithOlympicNumber1Exists(int olympicNumber1);
    void save(Game game);
}

