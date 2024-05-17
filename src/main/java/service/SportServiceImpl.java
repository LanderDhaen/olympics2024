package service;

import domain.Discipline;
import domain.Sport;
import domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SportRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class SportServiceImpl implements SportService {

    @Autowired
    private SportRepository sportRepository;

    @Override
    public List<Sport> findAllSports() {
        return sportRepository.findAll();
    }

    public Sport findByName(String name) {

        Sport sport = sportRepository.findByName(name);

        for(Discipline discipline : sport.getDisciplines()) {
            discipline.getGames().sort(Comparator.comparing(Game::getDate));
        }

        return sport;
    }
}
