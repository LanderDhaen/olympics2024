package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Spectator;


public interface SpectatorRepository extends JpaRepository<Spectator, Long> {

    Spectator findByUsername(String username);
}
