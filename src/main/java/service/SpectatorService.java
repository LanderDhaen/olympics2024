package service;

import domain.Game;
import domain.Spectator;

public interface SpectatorService {
    Spectator findByUsername(String name);
    void buyTickets(Spectator spectator, Game game, int amount);
}


