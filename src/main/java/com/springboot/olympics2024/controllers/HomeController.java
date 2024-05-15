package com.springboot.olympics2024.controllers;


import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.SportRepository;

@Controller
@RequestMapping("olympics2024")
public class HomeController {

   @Autowired
   private SportRepository sportRepository;

   @GetMapping
    public String olympics2024(Model model) {

       model.addAttribute("sports", sportRepository.findAll());

       return "olympics2024";
    }

}
