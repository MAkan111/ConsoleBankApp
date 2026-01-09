package ru.makan1.bankapp.repository;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryUserRepository {

    private final Map<String, User> dataStore = new ConcurrentHashMap<>();

    public void save(User user) {
        dataStore.put(key(user.getId()), user);
    }

    public User findById(Long id) {
        return dataStore.get(key(id));
    }

    public Collection<User> findAll() {
        return dataStore.values();
    }

    public void deleteById(Long id) {
        dataStore.remove(key(id));
    }

    private String key(Long id) {
        return id.toString();
    }

    public User findByLogin(String login) {
        return dataStore.values().stream()
                .filter(u -> login.equalsIgnoreCase(u.getLogin()))
                .findFirst()
                .orElse(null);
    }
}
