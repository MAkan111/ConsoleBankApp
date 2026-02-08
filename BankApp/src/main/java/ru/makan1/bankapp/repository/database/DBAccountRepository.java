package ru.makan1.bankapp.repository.database;

import org.springframework.stereotype.Repository;
import ru.makan1.bankapp.config.TransactionManager;
import ru.makan1.bankapp.model.Account;
import ru.makan1.bankapp.repository.AppRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class DBAccountRepository implements AppRepository<Account, Long> {

    private final TransactionManager transactionManager;

    public DBAccountRepository(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void save(Account entity) {
        transactionManager.currentSession().persist(entity);
    }

    @Override
    public void deleteById(Long id) {
        transactionManager.currentSession()
                .createMutationQuery("DELETE FROM Account a WHERE a.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void update(Account entity) {
        transactionManager.currentSession().merge(entity);
    }

    @Override
    public Optional<Account> findById(Long id) {
        String sql = "SELECT a FROM Account a WHERE a.id = :id";
        Account entity = transactionManager.currentSession()
                .createQuery(sql, Account.class)
                .setParameter("id", id)
                .getSingleResult();
        return Optional.ofNullable(entity);
    }

    @Override
    public List<Account> findAll() {
        String sql = "SELECT a FROM Account a";
        return transactionManager.currentSession()
                .createQuery(sql, Account.class)
                .getResultList();
    }

    public Optional<Account> findByUserId(Long userId) {
        String sql = "SELECT a FROM Account a WHERE a.user.id = :id";
        Account entity = transactionManager.currentSession()
                .createQuery(sql, Account.class)
                .setParameter("id", userId)
                .getSingleResult();
        return Optional.ofNullable(entity);
    }
}
