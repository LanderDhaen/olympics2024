package com.springboot.olympics2024.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeController {

    @GetMapping("/olympics2024")
    public String olympics2024() {
        return "olympics2024";
    }

}
