package com.springboot.olympics2024.controllers;

import domain.Game;
import domain.Spectator;
import domain.Ticket;
import domain.TicketForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.GameService;
import service.SpectatorService;
import validator.TicketValidator;

import java.util.Optional;

@Controller
@RequestMapping("/olympics2024")
public class BuyController {

    @Autowired
    private GameService gameService;

    @Autowired
    SpectatorService spectatorService;

    @Autowired
    TicketValidator ticketValidator;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/sports/{name}/games/{id}/buy")
    public String showForm(@PathVariable Long id, Model model, Authentication authentication) {

        Spectator spectator = spectatorService.findByUsername(authentication.getName());
        Optional<Game> gameOptional = gameService.findById(id);
        Game game = gameOptional.get();

        model.addAttribute("spectator", spectator);
        model.addAttribute("game", game);
        model.addAttribute("name", game.getSport().getName().toLowerCase());
        model.addAttribute("ticketForm", new TicketForm());

        return "ticketform";
    }

    @PostMapping("/sports/{name}/games/{id}/buy")
    public String buyTickets(@PathVariable Long id, @PathVariable String name, @Valid TicketForm ticketForm, BindingResult result, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {

        Spectator spectator = spectatorService.findByUsername(authentication.getName());
        Optional<Game> gameOptional = gameService.findById(id);
        Game game = gameOptional.get();

        ticketForm.setGame(game);
        ticketForm.setSpectator(spectator);

        ticketValidator.validate(ticketForm, result);

        if(result.hasErrors()) {
            model.addAttribute("spectator", spectator);
            model.addAttribute("game", game);
            model.addAttribute("name", game.getSport().getName().toLowerCase());
            return "ticketform";
        }

        int amount = ticketForm.getAmount();

        game.setRemainingSeats(game.getRemainingSeats() - amount);
        gameService.save(game);
        spectatorService.buyTickets(spectator, game, amount);

        redirectAttributes.addFlashAttribute("success", messageSource.getMessage("ticket.success", new Object[]{amount}, LocaleContextHolder.getLocale()));
        return "redirect:/olympics2024/sports/{name}";
    }

}
