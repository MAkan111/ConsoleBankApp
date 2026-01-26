package ru.makan1.bankapp.service;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.config.TransactionManager;

import java.util.function.Supplier;

@Component
public class TransactionHelper {

    private final TransactionManager transactionManager;

    public TransactionHelper(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public <T> T executeAndGet(Supplier<T> action) throws Exception {
        transactionManager.begin();
        try {
            T result = action.get();
            transactionManager.commit();
            return result;
        } catch (RuntimeException e) {
            transactionManager.rollback();
            throw e;
        }
    }

    public void execute(Runnable action) throws Exception {
        transactionManager.begin();
        try {
            action.run();
            transactionManager.commit();
        } catch (RuntimeException e) {
            transactionManager.rollback();
            throw e;
        }
    }
}