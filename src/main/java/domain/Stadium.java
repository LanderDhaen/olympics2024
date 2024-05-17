package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stadium implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private int capacity;

    @OneToMany(mappedBy = "stadium")
    private List<Game> games;

    public Stadium(String name, int capacity, List<Game> games) {
        this.name = name;
        this.capacity = capacity;
        this.games = games;
    }

    public Stadium(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }
}
