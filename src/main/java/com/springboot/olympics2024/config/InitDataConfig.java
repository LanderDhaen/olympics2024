package com.springboot.olympics2024.config;

import domain.Discipline;
import domain.Game;
import domain.Spectator;
import domain.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import repository.DisciplineRepository;
import repository.GameRepository;
import repository.SpectatorRepository;
import repository.SportRepository;
import util.Role;

import java.time.LocalDateTime;
import java.time.Month;

@Component
public class InitDataConfig implements CommandLineRunner {

    @Autowired
    private SportRepository sportRepository;
    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private SpectatorRepository spectatorRepository;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {

        // Seeding database with spectators

        spectatorRepository.save(new Spectator("User", encoder.encode("12345678"), Role.USER));
        spectatorRepository.save(new Spectator("Admin", encoder.encode("12345678"), Role.ADMIN));

        // Seeding database with sports

        Sport swimming = sportRepository.save(new Sport("Swimming"));
        Sport waterpolo = sportRepository.save(new Sport("Water Polo"));
        Sport rugby = sportRepository.save(new Sport("Rugby"));
        Sport basketball = sportRepository.save(new Sport("Basketball"));
        Sport cycling = sportRepository.save(new Sport("Cycling"));
        Sport canoe = sportRepository.save(new Sport("Canoe"));

        // Seeding database with disciplines

        disciplineRepository.save(new Discipline("400m Freestyle", true, swimming));
        disciplineRepository.save(new Discipline("50m Breaststroke", false, swimming));

        disciplineRepository.save(new Discipline("Seniors", true, waterpolo));
        disciplineRepository.save(new Discipline("Seniors", false, waterpolo));
        
        disciplineRepository.save(new Discipline("Sevens", true, rugby));
        disciplineRepository.save(new Discipline("Sevens", false, rugby));

        Discipline mens3x3 = disciplineRepository.save(new Discipline("3x3", true, basketball));
        Discipline womens3x3 = disciplineRepository.save(new Discipline("3x3", false, basketball));

        disciplineRepository.save(new Discipline("Road", true, cycling));
        disciplineRepository.save(new Discipline("Road", false, cycling));
        disciplineRepository.save(new Discipline("Track", true, cycling));
        disciplineRepository.save(new Discipline("Track", false, cycling));

        Discipline mensCanoeSlalom = disciplineRepository.save(new Discipline("Slalom", true, canoe));
        Discipline womensCanoeSlalom = disciplineRepository.save(new Discipline("Slalom", false, canoe));
        Discipline mensCanoeSprint = disciplineRepository.save(new Discipline("Sprint", true, canoe));
        Discipline womensCanoeSprint = disciplineRepository.save(new Discipline("Sprint", false, canoe));

        // Seeding the database with games

        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 30, 19, 40), "Place de la Concorde 1", 0, 99.99, mens3x3));
        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 30, 17, 30), "Place de la Concorde 1", 10, 99.99, mens3x3));
        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 30, 18, 35), "Place de la Concorde 1", 44, 99.99, womens3x3));

    }


}
