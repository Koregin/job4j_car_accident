package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccidentService {
    private final AccidentRepository repository;

    public AccidentService(AccidentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<Accident> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void create(Accident accident) {
        repository.save(accident);
    }

    @Transactional
    public void update(Accident accident) {
        repository.save(accident);
    }

    @Transactional
    public Accident findAccidentById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public List<AccidentType> findAllTypes() {
        return repository.findAllTypes();
    }

    @Transactional
    public List<Rule> findAllRules() {
        return repository.findAllRules();
    }

    @Transactional
    public Set<Rule> findRulesForAccident(String[] ids) {
        Set<Rule> rules = new HashSet<>();
        if (ids != null) {
            Arrays.stream(ids).forEach(id -> rules.add(repository.findRuleById(Integer.parseInt(id))));
        }
        return rules;
    }
}
