package ru.makan1.bankapp.service;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.config.AccountProperties;
import ru.makan1.bankapp.exception.UserNotFoundException;
import ru.makan1.bankapp.model.Account;
import ru.makan1.bankapp.model.User;
import ru.makan1.bankapp.repository.InMemoryAccountRepository;
import ru.makan1.bankapp.repository.InMemoryUserRepository;

@Component
public class UserAccountService {
    private final InMemoryAccountRepository inMemoryAccountRepository;
    private final InMemoryUserRepository inMemoryUserRepository;
    private final AccountProperties accountProperties;

    public UserAccountService(InMemoryAccountRepository inMemoryAccountRepository,
                              InMemoryUserRepository inMemoryUserRepository,
                              AccountProperties accountProperties
    ) {
        this.inMemoryAccountRepository = inMemoryAccountRepository;
        this.inMemoryUserRepository = inMemoryUserRepository;
        this.accountProperties = accountProperties;
    }

    public void createUserAccount(String login) {
        User user = inMemoryUserRepository
                .findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с логином: " + login + " не найден"));
        Account account = new Account();
        account.setUserId(user.getId());
        account.setMoneyAmount(accountProperties.getDefaultAmount());

        inMemoryAccountRepository.save(account);
        user.addAccount(account);
        inMemoryUserRepository.save(user);
    }
}
