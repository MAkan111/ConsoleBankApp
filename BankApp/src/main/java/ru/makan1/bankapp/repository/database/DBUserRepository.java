package ru.makan1.bankapp.repository.database;

import org.springframework.stereotype.Repository;
import ru.makan1.bankapp.config.TransactionManager;
import ru.makan1.bankapp.model.User;
import ru.makan1.bankapp.repository.AppRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class DBUserRepository implements AppRepository<User, Long> {

    private final TransactionManager transactionManager;

    public DBUserRepository(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void save(User user) {
        transactionManager.currentSession().persist(user);
    }

    @Override
    public void deleteById(Long id) {
        transactionManager.currentSession()
                .createMutationQuery("DELETE FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void update(User user) {
        transactionManager.currentSession().merge(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT u FROM User u WHERE u.id = :id";
        return transactionManager.currentSession()
                .createQuery(sql, User.class)
                .setParameter("id", id)
                .uniqueResultOptional();
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT u FROM User u left join fetch u.accountList";
        return transactionManager.currentSession()
                .createQuery(sql, User.class)
                .getResultList();
    }

    public Optional<User> findByLogin(String login) {
        String sql = "SELECT u FROM User u WHERE u.login = :login";
        return transactionManager.currentSession()
                .createQuery(sql, User.class)
                .setParameter("login", login)
                .uniqueResultOptional();
    }
}
