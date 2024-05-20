package domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gameID")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "spectatorID")
    private Spectator spectator;

    public Ticket(Game game, Spectator spectator) {
        this.game = game;
        this.spectator = spectator;
    }
}
