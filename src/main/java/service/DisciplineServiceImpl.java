package service;

import domain.Discipline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.DisciplineRepository;

import java.util.List;

@Service
public class DisciplineServiceImpl implements DisciplineService {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Override
    public List<Discipline> findBySport(String name) {
        return disciplineRepository.findBySport(name);
    }
}
