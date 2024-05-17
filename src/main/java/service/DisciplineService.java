package service;

import domain.Discipline;

import java.util.List;

public interface DisciplineService {
    List<Discipline> findBySport(String name);
}
