package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.List;
import java.util.function.Function;

/*@Repository*/
public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public Accident save(Accident accident) {
        Integer id = (Integer) this.tx(session -> session.save(accident));
        accident.setId(id);
        return accident;
    }

    public List<Accident> getAll() {
        return this.tx(session -> session.createQuery("select distinct a from Accident a join fetch a.rules order by a.id", Accident.class).getResultList());
    }

    public Accident replace(Accident accident) {
        return (Accident) this.tx(session -> session.merge(accident));
    }

    public Accident findAccidentById(int id) {
        return this.tx(session -> session.createQuery("select a from Accident a join fetch a.rules where a.id = :fId", Accident.class)
                .setParameter("fId", id)
                .uniqueResult());
    }

    public List<AccidentType> findAllTypes() {
        return this.tx(session -> session.createQuery("from AccidentType", AccidentType.class).getResultList());
    }

    public List<Rule> findAllRules() {
        return this.tx(session -> session.createQuery("from Rule", Rule.class).getResultList());
    }

    public Rule findRuleById(int id) {
        return this.tx(session -> session.get(Rule.class, id));
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
