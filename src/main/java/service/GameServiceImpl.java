package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.GameRepository;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;

    @Override
    public boolean gameWithOlympicNumber1Exists(int olympicNumber1) {
        return gameRepository.findByOlympicNumber1(olympicNumber1) != null;
    }
}
