package com.springboot.olympics2024.config;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import repository.*;
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
    @Autowired
    private StadiumRepository stadiumRepository;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {

        // Seeding database with spectators

        spectatorRepository.save(new Spectator("User", encoder.encode("12345678"), Role.USER));
        spectatorRepository.save(new Spectator("Admin", encoder.encode("12345678"), Role.ADMIN));

        // Seeding database with stadiums

        Stadium vaires = stadiumRepository.save(new Stadium("Vaires-sur-Marne Nautical Stadium", 40));
        Stadium concorde = stadiumRepository.save(new Stadium("Place de la Concorde 1", 30));

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

        Discipline mensCanoeSlalom = disciplineRepository.save(new Discipline("Canoe Single Slalom", true, canoe));
        Discipline womensCanoeSlalom = disciplineRepository.save(new Discipline("Canoe Single Slalom", false, canoe));
        Discipline mensCanoeSprint = disciplineRepository.save(new Discipline("Canoe Single 200m", true, canoe));
        Discipline womensCanoeSprint = disciplineRepository.save(new Discipline("Canoe Single 200m", false, canoe));

        // Seeding the database with games
    /*
        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 31, 18, 35), "Place de la Concorde 1", 0, 99.99, basketball, List.of(mens3x3)));
        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 30, 17, 30), "Place de la Concorde 1", 10, 99.99, basketball, List.of(mens3x3)));
        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 30, 18, 35), "Place de la Concorde 1", 44, 99.99, basketball, List.of(womens3x3)));

        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 27, 15, 40), 25000, 25099, 0, 99.99, List.of(womensCanoeSlalom, mensCanoeSlalom), vaires, canoe));
        gameRepository.save(new Game(LocalDateTime.of(2024, Month.AUGUST, 8, 10, 30), 12356, 12346, 34, 49.99, List.of(womensCanoeSprint, mensCanoeSprint), vaires, canoe));


     */
    }
}
