package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.List;

@Repository
public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public Accident save(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(accident);
            session.getTransaction().commit();
            return accident;
        }
    }

    public List<Accident> getAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Accident", Accident.class)
                    .getResultList();
        }
    }

    public Accident replace(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(accident);
            session.getTransaction().commit();
            return accident;
        }
    }

    public Accident findAccidentById(int id) {
        try (Session session = sf.openSession()) {
            return session.get(Accident.class, id);
        }
    }

    public List<AccidentType> findAllTypes() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from AccidentType", AccidentType.class)
                    .getResultList();
        }
    }

    public List<Rule> findAllRules() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Rule", Rule.class)
                    .getResultList();
        }
    }

    public Rule findRuleById(int id) {
        try (Session session = sf.openSession()) {
            return session.get(Rule.class, id);
        }
    }
}
