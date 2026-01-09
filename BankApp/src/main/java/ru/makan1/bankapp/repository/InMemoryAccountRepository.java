package ru.makan1.bankapp.repository;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.model.Account;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryAccountRepository {

    private final Map<String, Account> dataStore = new ConcurrentHashMap<>();

    public void save(Account account) {
        dataStore.put(account.getId().toString(), account);
    }

    public Account findById(Long id) {
        return dataStore.get(key(id));
    }

    public Collection<Account> findAll() {
        return dataStore.values();
    }

    public List<Account> findByUserId(Long userId) {
        return dataStore.values().stream()
                .filter(a -> Objects.equals(a.getUserId(), userId))
                .toList();
    }

    public void deleteById(Long id) {
        dataStore.remove(key(id));
    }

    private String key(Long id) {
        return id.toString();
    }
}
