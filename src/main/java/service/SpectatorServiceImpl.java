package service;

import domain.Game;
import domain.Spectator;
import domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SpectatorRepository;
import repository.TicketRepository;

@Service
public class SpectatorServiceImpl implements SpectatorService {

    @Autowired
    private SpectatorRepository spectatorRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Spectator findByUsername(String username) {
        return spectatorRepository.findByUsername(username);
    }

    @Override
    public void buyTickets(Spectator spectator, Game game, int amount) {

        for (int i = 0; i < amount; i++) {
            ticketRepository.save(new Ticket(game, spectator));
        }
    }

}
