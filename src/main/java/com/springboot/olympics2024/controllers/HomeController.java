package com.springboot.olympics2024.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.SpectatorService;
import service.SportService;

@Controller
@RequestMapping("olympics2024")
public class HomeController {

   @Autowired
   private SportService sportService;

   @Autowired
   private SpectatorService spectatorService;

   @GetMapping
    public String olympics2024(Model model, Authentication authentication) {

       model.addAttribute("sports", sportService.findAllSports());
       model.addAttribute("tickets", spectatorService.findByUsername(authentication.getName()).getTickets());

       return "olympics2024";
    }
}
