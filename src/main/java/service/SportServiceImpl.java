package service;

import domain.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SportRepository;

import java.util.List;

@Service
public class SportServiceImpl implements SportService {

    @Autowired
    private SportRepository sportRepository;

    @Override
    public List<Sport> findAllSports() {
        return sportRepository.findAll();
    }

    public Sport findByName(String name) {
        return sportRepository.findByName(name);
    }
}
