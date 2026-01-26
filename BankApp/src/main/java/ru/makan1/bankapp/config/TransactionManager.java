package ru.makan1.bankapp.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionManager {

    private final SessionFactory sessionFactory;

    private final ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Integer> nestingLevel = ThreadLocal.withInitial(() -> 0);

    public TransactionManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session currentSession() {
        Session session = sessionThreadLocal.get();
        if (session == null) {
            throw new IllegalStateException("Нет активной транзакции.");
        }
        return session;
    }

    public void begin() {
        int level = this.nestingLevel.get();

        if (level == 0) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            sessionThreadLocal.set(session);
        }

        nestingLevel.set(level + 1);
    }

    public void commit() {
        int level = this.nestingLevel.get();

        if (level == 0) {
            throw new IllegalStateException("Нет активной транзакции");
        }

        nestingLevel.set(level - 1);

        if (level == 1) {
            try (Session session = sessionThreadLocal.get()) {
                if (session != null && session.getTransaction().isActive()) {
                    session.getTransaction().commit();
                }
            } finally {
                sessionThreadLocal.remove();
                nestingLevel.remove();
            }
        }
    }

    public void rollback() {
        Session session = sessionThreadLocal.get();

        try {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
            sessionThreadLocal.remove();
            nestingLevel.remove();
        }
    }
}
