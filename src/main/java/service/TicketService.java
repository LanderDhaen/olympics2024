package service;

import domain.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> sortTicketsBySportAndDate(List<Ticket> tickets);
}
