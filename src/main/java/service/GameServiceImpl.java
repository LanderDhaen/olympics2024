package service;

import domain.Game;
import exceptions.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import repository.GameRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    MessageSource messageSource;

    @Override
    public boolean gameWithOlympicNumber1Exists(int olympicNumber1) {
        return gameRepository.findByOlympicNumber1(olympicNumber1) != null;
    }

    @Override
    public void save(Game game) {
        gameRepository.save(game);
    }

    @Override
    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public List<Game> findGamesBySport(Long id) {
        return gameRepository.findGamesBySport(id);
    }

    @Override
    public Integer findRemainingSeatsByGame(Long id) {

        Integer seats = gameRepository.findRemainingSeatsByGame(id);

        if(seats == null) {
            throw new GameNotFoundException(messageSource.getMessage("exception.game.notFound", new Object[]{id}, LocaleContextHolder.getLocale()));
        }

        return seats;
    }
}
