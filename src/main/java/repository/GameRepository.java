package repository;

import domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByOlympicNumber1(int olympicNumber1);

    @Query("SELECT g FROM Game g WHERE g.sport.id = :id")
    List<Game> findGamesBySport(@Param("id") Long id);

    @Query("SELECT g.remainingSeats FROM Game g WHERE g.id = :id")
    Integer findRemainingSeatsByGame(@Param("id") Long id);

}
