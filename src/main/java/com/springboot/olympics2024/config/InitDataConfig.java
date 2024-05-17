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
import java.util.List;

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

        disciplineRepository.save(new Discipline("400m Freestyle", true));
        disciplineRepository.save(new Discipline("50m Breaststroke", false));

        disciplineRepository.save(new Discipline("Seniors", true));
        disciplineRepository.save(new Discipline("Seniors", false));

        disciplineRepository.save(new Discipline("Sevens", true));
        disciplineRepository.save(new Discipline("Sevens", false));

        Discipline mens3x3 = disciplineRepository.save(new Discipline("3x3", true));
        Discipline womens3x3 = disciplineRepository.save(new Discipline("3x3", false));

        disciplineRepository.save(new Discipline("Road", true));
        disciplineRepository.save(new Discipline("Road", false));
        disciplineRepository.save(new Discipline("Track", true));
        disciplineRepository.save(new Discipline("Track", false));

        Discipline mensCanoeSlalom = disciplineRepository.save(new Discipline("Canoe Single Slalom", true));
        Discipline womensCanoeSlalom = disciplineRepository.save(new Discipline("Canoe Single Slalom", false));
        Discipline mensCanoeSprint = disciplineRepository.save(new Discipline("Canoe Single 200m", true));
        Discipline womensCanoeSprint = disciplineRepository.save(new Discipline("Canoe Single 200m", false));

        // Seeding the database with games
    /*
        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 31, 18, 35), "Place de la Concorde 1", 0, 99.99, basketball, List.of(mens3x3)));
        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 30, 17, 30), "Place de la Concorde 1", 10, 99.99, basketball, List.of(mens3x3)));
        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 30, 18, 35), "Place de la Concorde 1", 44, 99.99, basketball, List.of(womens3x3)));

        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 27, 15, 40), "Vaires-sur-Marne Nautical Stadium", 0, 99.99, canoe, List.of(womensCanoeSlalom, mensCanoeSlalom)));
      */  gameRepository.save(new Game(LocalDateTime.of(2024, Month.AUGUST, 8, 10, 30), "Vaires-sur-Marne Nautical Stadium",1235, 1335, 1, 49.99, List.of(womensCanoeSprint, mensCanoeSprint), canoe));
    }
}
