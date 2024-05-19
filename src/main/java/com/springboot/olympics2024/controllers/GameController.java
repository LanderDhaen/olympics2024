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
import org.springframework.web.servlet.View;
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
    @Autowired
    private View error;

    @GetMapping("/games/create/{name}")
    public String showForm(@PathVariable("name") String name, Model model) {

        Sport sport = sportService.findByName(name);

        model.addAttribute("sport", sport.getName());
        model.addAttribute("disciplines", sport.getDisciplines());
        model.addAttribute("stadiums", stadiumService.findAllStadiums());
        model.addAttribute("game", new Game());

        return "gameform";
    }

    @PostMapping("/games/create/{name}")
    public String addGame(@Valid Game game, BindingResult result, @PathVariable("name") String name, Model model) {

        olympicNumberValidator.validate(game, result);
        remainingSeatsValidator.validate(game, result);

        Sport sport = sportService.findByName(name);

        if (result.hasErrors()) {
            model.addAttribute("sport", sport.getName());
            model.addAttribute("disciplines", sport.getDisciplines());
            model.addAttribute("stadiums", stadiumService.findAllStadiums());
            return "gameform";
        }

        game.setSport(sport);

        gameService.save(game);

        return "redirect:/olympics2024/sports/{name}";

    }

}
