package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    Map<Integer, Accident> accidents = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger(5);

    {
        accidents.put(1, new Accident(1, "accident1", "Врезался в бампер"));
        accidents.put(2, new Accident(2, "accident2", "Не уступил дорогу"));
        accidents.put(3, new Accident(3, "accident3", "Подрезал пешехода"));
        accidents.put(4, new Accident(4, "accident4", "Пересек две сплошные"));
        accidents.put(5, new Accident(5, "accident5", "Не уступил дорогу скорой"));
    }

    public List<Accident> findAll() {
        List<Accident> accidentList = new ArrayList<>();
        for (Integer key : accidents.keySet()) {
            accidentList.add(accidents.get(key));
        }
        return accidentList;
    }

    public Accident save(Accident accident) {
        accident.setId(counter.incrementAndGet());
        accidents.putIfAbsent(accident.getId(), accident);
        return accident;
    }

    public Accident replace(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldAccident) -> accident);
    }

    public Accident findById(int id) {
        return accidents.get(id);
    }
}
