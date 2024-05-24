package service;

import domain.Game;
import domain.Spectator;
import domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TicketRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public List<Ticket> sortTicketsBySportAndDate(List<Ticket> tickets) {
        return tickets.stream()
                .sorted(Comparator.comparing((Ticket ticket) -> ticket.getGame().getSport().getName())
                        .thenComparing(ticket -> ticket.getGame().getDate()))
                .toList();
    }

    @Override
    public void buyTickets(Spectator spectator, Game game, int amount) {

        for (int i = 0; i < amount; i++) {
            ticketRepository.save(new Ticket(game, spectator));
        }
    }

}
