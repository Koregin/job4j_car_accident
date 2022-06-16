package ru.job4j.accident.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccidentRepository extends CrudRepository<Accident, Integer> {

    @Override
    @Query("select distinct a from Accident a join fetch a.rules order by a.id")
    List<Accident> findAll();

    @Override
    @Query("select a from Accident a join fetch a.rules where a.id = :fId")
    Optional<Accident> findById(@Param("fId") Integer id);

    @Query("from AccidentType")
    List<AccidentType> findAllTypes();

    @Query("from Rule")
    List<Rule> findAllRules();

    @Query("select r from Rule r where r.id = :fId")
    Rule findRuleById(@Param("fId") int ruleId);
}
