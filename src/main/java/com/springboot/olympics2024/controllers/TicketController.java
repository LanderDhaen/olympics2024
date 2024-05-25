package com.springboot.olympics2024.controllers;

import domain.Spectator;
import domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.SpectatorService;
import service.TicketService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/olympics2024")
public class TicketController {

    @Autowired
    private SpectatorService spectatorService;
    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public String tickets(Model model, Authentication authentication) {

        Spectator spectator = spectatorService.findByUsername(authentication.getName());

        List<Ticket> tickets = ticketService.sortTicketsBySportAndDate(spectator.getTickets());

        model.addAttribute("tickets", tickets);

        return "tickets";
    }

}
