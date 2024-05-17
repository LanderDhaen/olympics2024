package repository;

import domain.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

    @Query("SELECT d FROM Discipline d JOIN d.sport s WHERE s.name = :name")
    List<Discipline> findBySport(@Param("name") String name);

}
