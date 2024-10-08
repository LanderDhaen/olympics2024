package domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Discipline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean gender;

    @ManyToOne
    @JsonBackReference
    private Sport sport;

    @ManyToMany
    @JoinTable(
            name = "discipline/game",
            joinColumns = @JoinColumn(name = "disciplineid"),
            inverseJoinColumns = @JoinColumn(name = "gameid")
    )
    @JsonBackReference
    private List<Game> games;


        public Discipline(String name, boolean gender, Sport sport) {
        this.name = name;
        this.gender = gender;
        this.sport = sport;
    }
}
