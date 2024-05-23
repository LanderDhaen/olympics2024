package service;

import domain.Ticket;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Override
    public List<Ticket> sortTicketsBySportAndDate(List<Ticket> tickets) {
        return tickets.stream()
                .sorted(Comparator.comparing((Ticket ticket) -> ticket.getGame().getSport().getName())
                        .thenComparing(ticket -> ticket.getGame().getDate()))
                .toList();
    }

}
