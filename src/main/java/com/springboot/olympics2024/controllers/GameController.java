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
import service.GameService;
import service.SportService;
import service.StadiumService;
import validator.OlympicNumberValidator;
import validator.RemainingSeatsValidator;

import java.util.List;

@Controller
@RequestMapping("/olympics2024")
public class GameController {

    @Autowired
    private SportService sportService;

    @Autowired
    private GameService gameService;

    @Autowired
    private StadiumService stadiumService;

    @Autowired
    private OlympicNumberValidator olympicNumberValidator;
    @Autowired
    private RemainingSeatsValidator remainingSeatsValidator;

    @GetMapping("/games/create/{name}")
    public String showForm(@PathVariable("name") String name, Model model) {

        model.addAttribute("sport", name);
        model.addAttribute("stadiums", stadiumService.findAllStadiums());
        model.addAttribute("game", new Game());

        return "gameform";
    }

    @PostMapping("/games/create/{name}")
    public String addGame(@Valid Game game, BindingResult result, @PathVariable("name") String name, Model model) {

        olympicNumberValidator.validate(game, result);
        remainingSeatsValidator.validate(game, result);

        if (result.hasErrors()) {
            model.addAttribute("sport", name);
            model.addAttribute("stadiums", stadiumService.findAllStadiums());
            return "gameform";
        }

        Sport sport = sportService.findByName(name);
        game.setSport(sport);

        gameService.save(game);

        return "redirect:/olympics2024/sports/{name}";

    }

}
