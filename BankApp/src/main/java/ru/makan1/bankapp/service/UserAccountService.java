package ru.makan1.bankapp.service;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.config.AccountProperties;
import ru.makan1.bankapp.model.Account;
import ru.makan1.bankapp.model.User;
import ru.makan1.bankapp.repository.InMemoryAccountRepository;
import ru.makan1.bankapp.repository.InMemoryUserRepository;

import java.util.ArrayList;

@Component
public class UserAccountService {
    private final InMemoryAccountRepository inMemoryAccountRepository;
    private final InMemoryUserRepository inMemoryUserRepository;
    private final AccountProperties accountProperties;

    public UserAccountService(InMemoryAccountRepository inMemoryAccountRepository, InMemoryUserRepository inMemoryUserRepository, AccountProperties accountProperties) {
        this.inMemoryAccountRepository = inMemoryAccountRepository;
        this.inMemoryUserRepository = inMemoryUserRepository;
        this.accountProperties = accountProperties;
    }

    public void createUserAccount(String login) {
        User user = inMemoryUserRepository.findByLogin(login);
        Account account = new Account();
        account.setUserId(user.getId());
        account.setMoneyAmount(accountProperties.getDefaultAmount());
        inMemoryAccountRepository.save(account);

        if (user.getAccountList() == null) {
            user.setAccountList(new ArrayList<>());
        }
        user.getAccountList().add(account);

        inMemoryUserRepository.save(user);
    }
}
