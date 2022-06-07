package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(5);
    private final List<AccidentType> types = new ArrayList<>();
    private final List<Rule> rules = new ArrayList<>();

    public AccidentMem() {
        accidents.put(1, new Accident(1, "accident1", "Врезался в бампер"));
        accidents.put(2, new Accident(2, "accident2", "Не уступил дорогу"));
        accidents.put(3, new Accident(3, "accident3", "Подрезал пешехода"));
        accidents.put(4, new Accident(4, "accident4", "Пересек две сплошные"));
        accidents.put(5, new Accident(5, "accident5", "Не уступил дорогу скорой"));
        types.add(AccidentType.of(1, "Две машины"));
        types.add(AccidentType.of(2, "Машина и человек"));
        types.add(AccidentType.of(3, "Машина и велосипед"));
        rules.add(Rule.of(1, "Статья. 1"));
        rules.add(Rule.of(2, "Статья. 2"));
        rules.add(Rule.of(3, "Статья. 3"));
    }

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public List<AccidentType> findAllTypes() {
        return types;
    }

    public List<Rule> findAllRules() {
        return rules;
    }

    public Rule findRuleById(int id) {
        return rules.get(rules.indexOf(new Rule(id)));
    }

    public Accident save(Accident accident) {
        accident.setId(counter.incrementAndGet());
        AccidentType type = accident.getType();
        type.setName(types.get(type.getId() - 1).getName());
        accidents.putIfAbsent(accident.getId(), accident);
        return accident;
    }

    public Accident replace(Accident accident) {
        AccidentType type = accident.getType();
        type.setName(types.get(type.getId() - 1).getName());
        return accidents.computeIfPresent(accident.getId(), (id, oldAccident) -> accident);
    }

    public Accident findById(int id) {
        return accidents.get(id);
    }
}
