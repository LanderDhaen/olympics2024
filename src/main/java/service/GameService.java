package service;

import domain.Game;

import java.util.List;
import java.util.Optional;

public interface GameService {
    boolean gameWithOlympicNumber1Exists(int olympicNumber1);
    void save(Game game);
    Optional<Game> findById(Long id);
    List<Game> findGamesBySport(Long id);
    int findRemainingSeatsByGame(Long id);
}

