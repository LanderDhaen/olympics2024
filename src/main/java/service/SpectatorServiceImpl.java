package service;

import domain.Spectator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SpectatorRepository;

@Service
public class SpectatorServiceImpl implements SpectatorService {

    @Autowired
    private SpectatorRepository spectatorRepository;

    @Override
    public Spectator findByUsername(String username) {
        return spectatorRepository.findByUsername(username);
    }
}
