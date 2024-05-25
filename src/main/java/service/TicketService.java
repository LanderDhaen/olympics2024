package service;

import domain.Game;
import domain.Spectator;
import domain.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> sortTicketsBySportAndDate(List<Ticket> tickets);
    void buyTickets(Spectator spectator, Game game, int amount);
}
