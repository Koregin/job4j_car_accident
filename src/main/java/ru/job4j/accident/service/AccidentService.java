package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;

import java.util.List;

@Service
public class AccidentService {
    private final AccidentMem repository;

    public AccidentService(AccidentMem repository) {
        this.repository = repository;
    }

    public List<Accident> findAll() {
        return repository.findAll();
    }
}
