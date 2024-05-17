package com.springboot.olympics2024.controllers;

import domain.Game;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/olympics2024")
public class GameController {


    @GetMapping("/games/create/{name}")
    public String showForm(Model model) {
        model.addAttribute("game", new Game());
        return "gameform";
    }

    @PostMapping("/games/create/{name}")
    public String addGame(@Valid Game game, BindingResult result) {

        if (result.hasErrors()) {
            return "gameform";
        }

        return ":redirect/olympics2024";

    }

}
