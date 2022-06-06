package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class AccidentMem {
    Map<Integer, Accident> accidents;

    {
        accidents = Map.of(1, new Accident(1, "accident1", "Врезался в бампер"),
                2, new Accident(2, "accident2", "Не уступил дорогу"),
                3, new Accident(3, "accident3", "Не увидел дерево"),
                4, new Accident(4, "accident4", "Пересек две сплошные"),
                5, new Accident(5, "accident5", "Не уступил дорогу скорой")
        );
    }

    public List<Accident> findAll() {
        List<Accident> accidentList = new ArrayList<>();
        for (Integer key : accidents.keySet()) {
            accidentList.add(accidents.get(key));
        }
        return accidentList;
    }
}
