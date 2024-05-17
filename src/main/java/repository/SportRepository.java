package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import domain.Sport;

public interface SportRepository extends JpaRepository<Sport, Long> {

    Sport findByName(String name);
}
