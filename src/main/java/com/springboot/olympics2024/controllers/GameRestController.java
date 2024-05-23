package com.springboot.olympics2024.controllers;

import java.util.List;

import domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.GameService;


@RestController
@RequestMapping(value = "/rest")
public class GameRestController {

    @Autowired
    private GameService gameService;

    @GetMapping(value = "/sports/{id}/games")
    public List<Game> getGamesBySport(@PathVariable("id") Long id) {
        return gameService.findGamesBySport(id);
    }

    @GetMapping(value = "/games/{id}/seats")
    public int getRemainingSeatsByGame(@PathVariable("id") Long id) {
        return gameService.findRemainingSeatsByGame(id);
    }

}
