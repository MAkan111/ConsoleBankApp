package ru.makan1.bankapp.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionManager {

    private final SessionFactory sessionFactory;

    private final ThreadLocal<Session> transaction = new ThreadLocal<>();

    public TransactionManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session currentSession(){
        return transaction.get();
    }

    public void begin(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        transaction.set(session);
    }

    public void commit(){
        Session session = transaction.get();
        session.getTransaction().commit();
        session.close();
        transaction.remove();
    }

    public void rollback(){
        Session session = transaction.get();
        session.getTransaction().rollback();
        session.close();
        transaction.remove();
    }
}
