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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.SpectatorService;
import service.SportService;

import java.util.Map;

@Controller
@RequestMapping("/olympics2024")
public class SportController {

    @Autowired
    private SportService sportService;

    @Autowired
    private SpectatorService spectatorService;

    @GetMapping("/sports/{name}")
    public String sportdetail(@PathVariable("name") String name, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {

        Sport sport = sportService.findByName(name);
        Spectator spectator = spectatorService.findByUsername(authentication.getName());

        model.addAttribute("sport", sport);

        Map<Long, Integer> ticketAmounts = sportService.calculateTicketAmounts(sport, spectator);

        for (Map.Entry<Long, Integer> entry : ticketAmounts.entrySet()) {
            model.addAttribute("count_" + entry.getKey(), entry.getValue());
        }

        if (redirectAttributes.containsAttribute("success")) {
            model.addAttribute("success", redirectAttributes.getAttribute("success"));
        }

        return "sportdetail";
    }
}
