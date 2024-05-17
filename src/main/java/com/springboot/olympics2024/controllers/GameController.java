package com.springboot.olympics2024.controllers;

import domain.Game;
import domain.Sport;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.SportService;

@Controller
@RequestMapping("/olympics2024")
public class GameController {

    @Autowired
    private SportService sportService;

    @GetMapping("/games/create/{name}")
    public String showForm(@PathVariable("name") String name, Model model) {

        Sport sport = sportService.findByName(name);

        model.addAttribute("game", new Game());
        model.addAttribute("sport", sport);

        return "gameform";
    }

    @PostMapping("/games/create/{name}")
    public String addGame(@Valid Game game, BindingResult result, @PathVariable("name") String name, Model model) {

        if (result.hasErrors()) {
            Sport sport = sportService.findByName(name);
            model.addAttribute("sport", sport);
            return "gameform";
        }

        return ":redirect/olympics2024";

    }

}
