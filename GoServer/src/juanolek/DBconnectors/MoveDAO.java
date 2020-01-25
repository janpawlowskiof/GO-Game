package juanolek.DBconnectors;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class MoveDAO {

    private Session currentSession;
    private Transaction currentTransaction;

    public MoveDAO(){
    }

    public Session openCurrentSession(){
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionWithTransaction(){
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Move.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }


    public void persist(Move entity) {
        openCurrentSessionWithTransaction();
        getCurrentSession().save(entity);
        closeCurrentSessionWithTransaction();
    }


    public void update(Move entity) {
        openCurrentSessionWithTransaction();
        getCurrentSession().update(entity);
        closeCurrentSessionWithTransaction();
    }


    public Move findById(String id) {
        openCurrentSession();
        Move book = (Move) getCurrentSession().get(Move.class,id);
        closeCurrentSession();
        return book;
    }


    public void delete(Move entity) {
        openCurrentSessionWithTransaction();
        getCurrentSession().delete(entity);
        closeCurrentSessionWithTransaction();
    }


    public List findAll() {
        openCurrentSession();
        List<Move> moves = (List<Move>) getCurrentSession().createQuery("from Move").list();
        closeCurrentSession();
        return moves;
    }


    public void deleteAll() {
        openCurrentSessionWithTransaction();
        List<Move> entityList = findAll();
        for (Move entity : entityList) {
            delete(entity);
        }
        closeCurrentSessionWithTransaction();
    }
}
