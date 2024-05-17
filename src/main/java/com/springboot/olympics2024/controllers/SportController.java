package com.springboot.olympics2024.controllers;

import domain.Discipline;
import domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.DisciplineRepository;
import service.DisciplineService;
import service.SportService;

@Controller
@RequestMapping("/olympics2024")
public class SportController {

    @Autowired
    private SportService sportService;

    @GetMapping("/sport/{name}")
    public String sportdetail(@PathVariable("name") String name, Model model) {
        model.addAttribute("sport", sportService.findByName(name));

        return "sportdetail";
    }
}
