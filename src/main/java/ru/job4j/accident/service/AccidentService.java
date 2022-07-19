package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.AccidentTypeRepository;
import ru.job4j.accident.repository.RuleRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccidentService {
    private final AccidentRepository accidentRepo;
    private final AccidentTypeRepository accidentTypeRepo;
    private final RuleRepository ruleRepo;

    public AccidentService(AccidentRepository accidentRepo,
                           AccidentTypeRepository accidentTypeRepo,
                           RuleRepository ruleRepo) {
        this.accidentRepo = accidentRepo;
        this.accidentTypeRepo = accidentTypeRepo;
        this.ruleRepo = ruleRepo;
    }


    @Transactional
    public List<Accident> findAll() {
        return accidentRepo.findAll();
    }

    @Transactional
    public void create(Accident accident) {
        accidentRepo.save(accident);
    }

    @Transactional
    public void update(Accident accident) {
        accidentRepo.save(accident);
    }

    public void delete(int id) {
        accidentRepo.delete(findAccidentById(id));
    }

    @Transactional
    public Accident findAccidentById(int id) {
        return accidentRepo.findById(id).orElse(null);
    }

    @Transactional
    public List<AccidentType> findAllTypes() {
        return accidentTypeRepo.findAll();
    }

    @Transactional
    public List<Rule> findAllRules() {
        return ruleRepo.findAll();
    }

    @Transactional
    public Set<Rule> findRulesForAccident(String[] ids) {
        Set<Rule> rules = new HashSet<>();
        if (ids != null) {
            Arrays.stream(ids).forEach(id -> rules.add(ruleRepo.findById(Integer.parseInt(id)).orElse(null)));
        }
        return rules;
    }
}
