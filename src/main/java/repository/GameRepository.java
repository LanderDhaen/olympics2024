package repository;

import domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByOlympicNumber1(int olympicNumber1);
}
