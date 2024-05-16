package com.springboot.olympics2024.controllers;


import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.SportService;

@Controller
@RequestMapping("olympics2024")
public class HomeController {

   private SportService sportService;

   @GetMapping
    public String olympics2024(Model model) {

       model.addAttribute("sports", sportService.findAllSports());

       return "olympics2024";
    }

}
