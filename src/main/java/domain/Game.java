package domain;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.Setter;
import validator.ValidDate;
import validator.ValidDisciplines;

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

    @NotNull(message = "{validator.emptyInput}")
    @ValidDate
    private LocalDateTime date;

    @NotNull(message = "{validator.emptyInput}")
    private int olympicNumber1;

    @NotNull(message = "{validator.emptyInput}")
    private int olympicNumber2;

    @NotNull(message = "{validator.emptyInput}")
    private int remainingSeats;

    @NotNull(message = "{validator.emptyInput}")
    @DecimalMin(value = "0", inclusive = false, message = "{validator.validPrice.min}")
    @DecimalMax(value = "150", inclusive = false, message = "{validator.validPrice.max}")
    private Double ticketPrice;

    @ValidDisciplines(max = 2)
    @ManyToMany
    @JoinTable(
            name = "discipline/game",
            joinColumns = @JoinColumn(name = "gameid"),
            inverseJoinColumns = @JoinColumn(name = "disciplineid")
    )
    private List<Discipline> disciplines;

    @NotNull(message = "{validator.emptyInput}")
    @ManyToOne
    @JoinColumn(name = "stadiumID")
    private Stadium stadium;

    @ManyToOne
    @JoinColumn(name = "sportID")
    private Sport sport;

    public Game(LocalDateTime date, int olympicNumber1, int olympicNumber2, int remainingSeats, Double ticketPrice, List<Discipline> disciplines, Stadium stadium, Sport sport) {
        this.date = date;
        this.olympicNumber1 = olympicNumber1;
        this.olympicNumber2 = olympicNumber2;
        this.remainingSeats = remainingSeats;
        this.ticketPrice = ticketPrice;
        this.disciplines = disciplines;
        this.stadium = stadium;
        this.sport = sport;
    }
}


