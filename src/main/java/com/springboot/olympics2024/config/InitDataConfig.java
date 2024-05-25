package com.springboot.olympics2024.config;

import domain.*;
import org.antlr.v4.runtime.misc.LogManager;
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
    @Autowired
    private TicketRepository ticketRepository;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {

        // Seeding database with spectators

        Spectator user = spectatorRepository.save(new Spectator("User", encoder.encode("12345678"), Role.USER));
        Spectator lander = spectatorRepository.save(new Spectator("Lander", encoder.encode("87654321"), Role.USER));
        Spectator admin = spectatorRepository.save(new Spectator("Admin", encoder.encode("12345678"), Role.ADMIN));

        // Seeding database with stadiums

        Stadium aquatics = stadiumRepository.save(new Stadium("Aquatics Centre", 45));
        Stadium vaires = stadiumRepository.save(new Stadium("Vaires-sur-Marne Nautical Stadium", 40));
        Stadium concorde = stadiumRepository.save(new Stadium("Place de la Concorde 1", 30));
        Stadium stadedefrance = stadiumRepository.save(new Stadium("Stade de France", 25));
        Stadium invalides = stadiumRepository.save(new Stadium("Les Invalides", 20));

        // Seeding database with sports

        Sport swimming = sportRepository.save(new Sport("Swimming"));
        Sport waterpolo = sportRepository.save(new Sport("Water Polo"));
        Sport rugby = sportRepository.save(new Sport("Rugby"));
        Sport basketball = sportRepository.save(new Sport("Basketball"));
        Sport canoe = sportRepository.save(new Sport("Canoe"));
        Sport archery = sportRepository.save(new Sport("Archery"));

        // Seeding database with disciplines

        Discipline men400free = disciplineRepository.save(new Discipline("400m Freestyle", true, swimming));
        Discipline women50breast = disciplineRepository.save(new Discipline("50m Breaststroke", false, swimming));

        Discipline mensWaterpolo = disciplineRepository.save(new Discipline("Seniors", true, waterpolo));
        Discipline womenWwaterpolo = disciplineRepository.save(new Discipline("Seniors", false, waterpolo));

        Discipline mensSevens = disciplineRepository.save(new Discipline("Sevens", true, rugby));
        Discipline womensSevens = disciplineRepository.save(new Discipline("Sevens", false, rugby));

        Discipline mens3x3 = disciplineRepository.save(new Discipline("3x3", true, basketball));
        Discipline womens3x3 = disciplineRepository.save(new Discipline("3x3", false, basketball));

        Discipline mensArcheryIndividual = disciplineRepository.save(new Discipline("Individual", true, archery));
        Discipline mensArcheryTeam = disciplineRepository.save(new Discipline("Team", true, archery));

        Discipline mensCanoeSlalom = disciplineRepository.save(new Discipline("Canoe Single Slalom", true, canoe));
        Discipline womensCanoeSlalom = disciplineRepository.save(new Discipline("Canoe Single Slalom", false, canoe));
        Discipline mensCanoeSprint = disciplineRepository.save(new Discipline("Canoe Single 200m", true, canoe));
        Discipline womensCanoeSprint = disciplineRepository.save(new Discipline("Canoe Single 200m", false, canoe));

        // Seeding the database with games

        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 31, 18, 35), 55530, 55540, 5, 99.99, List.of(mens3x3), concorde,basketball));

        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 30, 17, 30), 10020, 100160, 10, 99.99, List.of(mens3x3), concorde, basketball));
        gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 30, 18, 35), 20203, 20403, 10, 99.99, List.of(mens3x3), concorde, basketball));

        Game womensCanoeSlalomSingle = gameRepository.save(new Game(LocalDateTime.of(2024, Month.JULY, 27, 15, 40), 25000, 25099, 0, 99.99, List.of(womensCanoeSlalom, mensCanoeSlalom), vaires, canoe));
        gameRepository.save(new Game(LocalDateTime.of(2024, Month.AUGUST, 8, 10, 30), 12356, 12346, 34, 49.99, List.of(womensCanoeSprint, mensCanoeSprint), vaires, canoe));

        // Seeding the database with tickets

        ticketRepository.save(new Ticket(womensCanoeSlalomSingle, user));
        ticketRepository.save(new Ticket(womensCanoeSlalomSingle, lander));
    }
}