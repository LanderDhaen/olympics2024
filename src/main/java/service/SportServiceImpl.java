package service;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SportRepository;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class SportServiceImpl implements SportService {

    @Autowired
    private SportRepository sportRepository;

    @Override
    public List<Sport> findAllSports() {
        return sportRepository.findAll();
    }

    @Override
    public Sport findByName(String name) {

        Sport sport = sportRepository.findByName(name);

        sport.getGames().sort(Comparator.comparing(Game::getDate));

        return sport;
    }

    @Override
    public Map<Long, Integer> calculateTicketAmounts(Sport sport, Spectator spectator) {

        Map<Long, Integer> ticketAmounts = new HashMap<>();

        for (Game game : sport.getGames()) {
            int count = 0;
            for (Ticket ticket : spectator.getTickets()) {
                if (ticket.getGame().getId().equals(game.getId())) {
                    count++;
                }
            }
            ticketAmounts.put(game.getId(), count);
        }

        return ticketAmounts;

    }
}
