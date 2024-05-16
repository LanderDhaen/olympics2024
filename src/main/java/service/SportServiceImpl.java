package service;

import domain.Sport;
import repository.SportRepository;

import java.util.List;

public class SportServiceImpl implements SportService {

    private SportRepository sportRepository;

    @Override
    public List<Sport> findAllSports() {
        return sportRepository.findAll();
    }
}
