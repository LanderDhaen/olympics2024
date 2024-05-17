package domain;

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

    @OneToMany(mappedBy = "discipline")
    private List<Game> games;

    @ManyToOne
    @JoinColumn(name = "sportID")
    private Sport sport;

        public Discipline(String name, boolean gender, Sport sport) {
        this.name = name;
        this.gender = gender;
        this.sport = sport;
    }
}
