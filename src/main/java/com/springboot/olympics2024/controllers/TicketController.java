package com.springboot.olympics2024.controllers;

import domain.Game;
import domain.Spectator;
import domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.GameService;
import service.SpectatorService;

import java.util.Optional;

@Controller
@RequestMapping("/olympics2024")
public class TicketController {

    @Autowired
    private GameService gameService;

    @Autowired
    SpectatorService spectatorService;

    private static final int MAX_GAME = 20;
    private static final int MAX_TOTAL = 100;

    @GetMapping("/sports/{name}/games/{id}/buy")
    public String showForm(@PathVariable Long id, Model model, Authentication authentication) {

        Spectator spectator = spectatorService.findByUsername(authentication.getName());
        Optional<Game> gameOptional = gameService.findById(id);
        Game game = gameOptional.get();

        model.addAttribute("spectator", spectator);
        model.addAttribute("game", game);
        model.addAttribute("name", game.getSport().getName().toLowerCase());

        return "ticketform";
    }

    @PostMapping("/sports/{name}/games/{id}/buy")
    public String buyTickets(@PathVariable Long id, @PathVariable String name, @RequestParam int tickets, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {

        Spectator spectator = spectatorService.findByUsername(authentication.getName());
        Optional<Game> gameOptional = gameService.findById(id);
        Game game = gameOptional.get();

        int ticketsForGame = 0;

        for(Ticket ticket : spectator.getTickets()) {
            if (ticket.getGame().getId().equals(game.getId())) {
                ticketsForGame++;
            }
        }

        int totalTickets = spectator.getTickets().size();

        if (ticketsForGame + tickets > MAX_GAME) {
            model.addAttribute("spectator", spectator);
            model.addAttribute("game", game);
            model.addAttribute("name", game.getSport().getName().toLowerCase());
            model.addAttribute("error", "Je kan niet meer dan 20 tickets voor 1 wedstrijd kopen.");
            return "ticketform";
        }

        if (totalTickets + tickets > MAX_TOTAL) {
            model.addAttribute("spectator", spectator);
            model.addAttribute("game", game);
            model.addAttribute("name", game.getSport().getName().toLowerCase());
            model.addAttribute("error", "In totaal kan je niet meer dan 100 tickets kopen voor alle wedstrijden van alle sporten.");
            return "ticketform";
        }

        if (tickets > game.getRemainingSeats()) {
            model.addAttribute("spectator", spectator);
            model.addAttribute("game", game);
            model.addAttribute("name", game.getSport().getName().toLowerCase());
            model.addAttribute("error", "Deze wedstrijd heeft maar 10 zitjes meer");
            return "ticketform";
        }

        game.setRemainingSeats(game.getRemainingSeats() - tickets);
        gameService.save(game);
        spectatorService.buyTickets(spectator, game, tickets);

        redirectAttributes.addFlashAttribute("success", tickets + "tickets");
        return "redirect:/olympics2024/sports/{name}";
    }

}
