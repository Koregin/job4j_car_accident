package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentHibernate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccidentService {
    private final AccidentHibernate repository;

    public AccidentService(AccidentHibernate repository) {
        this.repository = repository;
    }

    public List<Accident> findAll() {
        return repository.getAll();
    }

    public void create(Accident accident) {
        repository.save(accident);
    }

    public boolean update(Accident accident) {
        return repository.replace(accident) != null;
    }

    public Accident findAccidentById(int id) {
        return repository.findAccidentById(id);
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
