package com.springboot.olympics2024.controllers;

import domain.Game;
import domain.Spectator;
import domain.Sport;
import domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import service.SpectatorService;
import service.SportService;

@Controller
@RequestMapping("/olympics2024")
public class SportController {

    @Autowired
    private SportService sportService;

    @Autowired
    private SpectatorService spectatorService;

    @GetMapping("/sports/{name}")
    public String sportdetail(@PathVariable("name") String name, Model model, Authentication authentication) {

        Sport sport = sportService.findByName(name);
        Spectator spectator = spectatorService.findByUsername(authentication.getName());

        model.addAttribute("sport", sport);

        for(Game game : sport.getGames()) {

            int count = 0;

            for(Ticket ticket : spectator.getTickets()) {
                if (ticket.getGame().getId().equals(game.getId())) {
                    count++;
                }
            }

            model.addAttribute("count_" + game.getId().toString(), count);
        }

        return "sportdetail";
    }
}
