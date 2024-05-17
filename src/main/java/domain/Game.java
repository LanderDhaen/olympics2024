package domain;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.Setter;
import validator.ValidDate;
import validator.ValidOlympicNumber1;
import validator.ValidOlympicNumber2;
import validator.ValidTicketPrice;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)

public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ValidDate
    private LocalDateTime date;

    private String location;

    @NotNull
    @ValidOlympicNumber1
    private int olympicNumber1;

    @NotNull
    @ValidOlympicNumber2
    private int olympicNumber2;

    @NotNull
    @Min(value = 1, message = "{validator.validSeats.min}")
    @Max(value = 50, message = "{validator.validSeats.max}")
    private int remainingSeats;

    @NotNull
    @ValidTicketPrice
    private double ticketPrice;

    @ManyToMany
    @JoinTable(
            name = "discipline/game",
            joinColumns = @JoinColumn(name = "gameid"),
            inverseJoinColumns = @JoinColumn(name = "disciplineid")
    )
    private List<Discipline> disciplines;

    @ManyToOne
    @JoinColumn(name = "sportID")
    private Sport sport;

    public Game(LocalDateTime date, String location, int olympicNumber1, int olympicNumber2, int remainingSeats, double ticketPrice, List<Discipline> disciplines, Sport sport) {
        this.date = date;
        this.location = location;
        this.olympicNumber1 = olympicNumber1;
        this.olympicNumber2 = olympicNumber2;
        this.remainingSeats = remainingSeats;
        this.ticketPrice = ticketPrice;
        this.disciplines = disciplines;
        this.sport = sport;
    }
}
