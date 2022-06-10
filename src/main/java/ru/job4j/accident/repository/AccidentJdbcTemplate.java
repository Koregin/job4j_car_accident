package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Set;

@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;
    private final String ln = System.lineSeparator();

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Accident save(Accident accident) {
        String insertSql = "insert into accident (name, description, address, accident_type_id) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        /*
        Insert bindings accident and rules
         */
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accident_rule (accident_id, rule_id) values (?, ?)",
                    keyHolder.getKey(),
                    rule.getId()
            );
        }
        return accident;
    }

    public Accident replace(Accident accident) {
        jdbc.update("update accident set name = ?, description = ?, address = ?, accident_type_id = ? where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId());
        /*
        Update rules for accident
         */
        jdbc.update("delete from accident_rule where accident_id = ?",
                accident.getId());
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accident_rule (accident_id, rule_id) values (?, ?)",
                    accident.getId(),
                    rule.getId());
        }
        return accident;
    }

    public List<Accident> getAll() {
        return jdbc.query("select a.id as id, a.name as name, a.description as description, a.address as address, t.id as typeId, t.name as typeName " + ln
                        + " from accident a inner join accident_type t on t.id = a.accident_type_id",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("description"));
                    accident.setAddress(rs.getString("address"));
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("typeid"));
                    accidentType.setName(rs.getString("typename"));
                    accident.setType(accidentType);
                    accident.setRules(Set.copyOf(findRulesByAccidentId(accident.getId())));
                    return accident;
                });
    }

    private List<Rule> findRulesByAccidentId(int id) {
        return jdbc.query("select r.id as id, r.name as name " + ln
                        + " from accident_rule inner join rule r on r.id = accident_rule.rule_id " + ln
                        + " where accident_id = ?",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                },
                id);
    }

    public List<AccidentType> findAllTypes() {
        return jdbc.query("select id, name from accident_type",
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }

    public List<Rule> findAllRules() {
        return jdbc.query("select id, name from rule",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    public Accident findAccidentById(int id) {
        return jdbc.queryForObject("select a.id as id, a.name as name, a.description as description, a.address as address, t.id as typeId, t.name as typeName " + ln
                        + " from accident a inner join accident_type t on t.id = a.accident_type_id where a.id = ?",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("description"));
                    accident.setAddress(rs.getString("address"));
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("typeid"));
                    accidentType.setName(rs.getString("typename"));
                    accident.setType(accidentType);
                    accident.setRules(Set.copyOf(findRulesByAccidentId(accident.getId())));
                    return accident;
                },
                id);
    }

    public Rule findRuleById(int id) {
        return jdbc.queryForObject("select id, name from rule where id = ?",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                },
                id);
    }
}
