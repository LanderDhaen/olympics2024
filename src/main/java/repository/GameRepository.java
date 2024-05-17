package repository;

import domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByOlympicNumber1(int olympicNumber1);

    @Query("SELECT g.location FROM Game g WHERE g.sport.id = :sportId")
    Set<String> findLocationBySportId(Long sportId);
}
