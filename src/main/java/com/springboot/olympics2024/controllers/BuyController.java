package com.springboot.olympics2024.controllers;

import domain.Game;
import domain.Spectator;
import domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
public class BuyController {

    @Autowired
    private GameService gameService;

    @Autowired
    SpectatorService spectatorService;

    @Autowired
    private MessageSource messageSource;

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
            model.addAttribute("error", messageSource.getMessage("ticket.validTickets.maxGame", new Object[]{MAX_GAME}, LocaleContextHolder.getLocale()));
            return "ticketform";
        }

        if (totalTickets + tickets > MAX_TOTAL) {
            model.addAttribute("spectator", spectator);
            model.addAttribute("game", game);
            model.addAttribute("name", game.getSport().getName().toLowerCase());
            model.addAttribute("error", messageSource.getMessage("ticket.validTickets.maxTickets", new Object[]{MAX_TOTAL}, LocaleContextHolder.getLocale()));
            return "ticketform";
        }

        if (tickets > game.getRemainingSeats()) {
            model.addAttribute("spectator", spectator);
            model.addAttribute("game", game);
            model.addAttribute("name", game.getSport().getName().toLowerCase());
            model.addAttribute("error", messageSource.getMessage("ticket.validTickets.remainingSeats", new Object[]{game.getRemainingSeats()}, LocaleContextHolder.getLocale()));
            return "ticketform";
        }

        game.setRemainingSeats(game.getRemainingSeats() - tickets);
        gameService.save(game);
        spectatorService.buyTickets(spectator, game, tickets);

        redirectAttributes.addFlashAttribute("success", messageSource.getMessage("ticket.success", new Object[]{tickets}, LocaleContextHolder.getLocale()));
        return "redirect:/olympics2024/sports/{name}";
    }

}
