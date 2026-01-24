package ru.makan1.bankapp.repository;

import java.util.List;
import java.util.Optional;

public interface AppRepository<T, K> {

    void save(T entity);

    void update(T entity);

    void deleteById(K id);

    Optional<T> findById(K id);

    List<T> findAll();
}
