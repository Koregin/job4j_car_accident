package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentMem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccidentService {
    private final AccidentMem repository;

    public AccidentService(AccidentMem repository) {
        this.repository = repository;
    }

    public List<Accident> findAll() {
        return repository.findAll();
    }

    public void create(Accident accident) {
        repository.save(accident);
    }

    public boolean update(Accident accident) {
        return repository.replace(accident) != null;
    }

    public Accident findById(int id) {
        return repository.findById(id);
    }

    public List<AccidentType> findAllTypes() {
        return repository.findAllTypes();
    }

    public List<Rule> findAllRules() {
        return repository.findAllRules();
    }

    public Set<Rule> findRulesForAccident(String[] ids) {
        Set<Rule> rules = new HashSet<>();
        if (ids != null) {
            Arrays.stream(ids).forEach(id -> rules.add(repository.findRuleById(Integer.parseInt(id))));
        }
        return rules;
    }
}
