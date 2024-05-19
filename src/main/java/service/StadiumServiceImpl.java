package service;

import domain.Stadium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.StadiumRepository;
import util.SortUtil;

import java.util.List;

@Service
public class StadiumServiceImpl implements StadiumService {

    @Autowired
    StadiumRepository stadiumRepository;

    public List<Stadium> findAllStadiums() {
        return stadiumRepository.findAll(SortUtil.sortByAsc("name"));
    }
}
