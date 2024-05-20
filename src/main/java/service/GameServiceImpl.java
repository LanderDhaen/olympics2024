package service;

import domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.GameRepository;

import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;

    @Override
    public boolean gameWithOlympicNumber1Exists(int olympicNumber1) {
        return gameRepository.findByOlympicNumber1(olympicNumber1) != null;
    }

    @Override
    public void save(Game game) {
        gameRepository.save(game);
    }

    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }
}
