package domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private String location;

    private int remainingSeats;

    private double ticketPrice;

    @ManyToOne
    @JoinColumn(name = "disciplineID")
    private Discipline discipline;

    public Game(LocalDateTime  date, String location, int remainingSeats, double ticketPrice, Discipline discipline) {
        this.date = date;
        this.location = location;
        this.remainingSeats = remainingSeats;
        this.ticketPrice = ticketPrice;
        this.discipline = discipline;
    }
}
