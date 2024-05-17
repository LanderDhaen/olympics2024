package domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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

    public Game(LocalDateTime  date, String location, int remainingSeats, double ticketPrice, Sport sport, List<Discipline> disciplines) {
        this.date = date;
        this.location = location;
        this.remainingSeats = remainingSeats;
        this.ticketPrice = ticketPrice;
        this.sport = sport;
        this.disciplines = disciplines;
    }
}
