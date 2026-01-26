package ru.makan1.bankapp.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionManager {

    private final SessionFactory sessionFactory;

    private final ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<>();

    public TransactionManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session currentSession() {
        Session session = sessionThreadLocal.get();
        if (session == null) {
            session = sessionFactory.openSession();
            sessionThreadLocal.set(session);
        }
        return session;
    }

    public void begin() {
        Session session = sessionThreadLocal.get();
        if (session == null) {
            session = sessionFactory.openSession();
            session.beginTransaction();
            sessionThreadLocal.set(session);
        } else if (!session.getTransaction().isActive()) {
            session.getTransaction().begin();
        }
    }

    public void commit() {
        try (Session session = sessionThreadLocal.get()) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().commit();
            }
        } finally {
            sessionThreadLocal.remove();
        }
    }

    public void rollback() {
        try (Session session = sessionThreadLocal.get()) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            sessionThreadLocal.remove();
        }
    }
}
