package ru.makan1.bankapp.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import ru.makan1.bankapp.config.AccountProperties;
import ru.makan1.bankapp.model.Account;
import ru.makan1.bankapp.model.User;
import ru.makan1.bankapp.model.dto.AccountDto;
import ru.makan1.bankapp.model.dto.UserAccountDto;
import ru.makan1.bankapp.repository.database.DBAccountRepository;
import ru.makan1.bankapp.repository.database.DBUserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserService {

    private final DBUserRepository dbUserRepository;
    private final DBAccountRepository dbAccountRepository;
    private final AccountProperties accountProperties;
    private final TransactionHelper transactionHelper;

    public UserService(DBUserRepository dbUserRepository,
                       AccountProperties accountProperties,
                       TransactionHelper transactionHelper,
                       DBAccountRepository dbAccountRepository
    ) {
        this.dbUserRepository = dbUserRepository;
        this.accountProperties = accountProperties;
        this.transactionHelper = transactionHelper;
        this.dbAccountRepository = dbAccountRepository;
    }

    @Transactional
    public void createUser(String login) {
        transactionHelper.execute(() -> {
            if (dbUserRepository.findByLogin(login).isPresent()) {
                throw new IllegalArgumentException("Пользователь с таким логином уже есть");
            }

            User user = new User();
            user.setLogin(login);

            Account account = new Account();
            account.setMoneyAmount(accountProperties.getDefaultAmount());

            user.addAccount(account);
            dbUserRepository.save(user);
        });
    }

    public Collection<UserAccountDto> showAllUsers() {
        return transactionHelper.executeAndGet(() -> {
            List<User> users = dbUserRepository.findAll();
            List<Account> accounts = dbAccountRepository.findAll();

            Map<Long, List<AccountDto>> userIdToAccounts = accounts.stream()
                    .collect(Collectors.groupingBy(
                            a -> a.getUser().getId(),
                            Collectors.mapping(a -> new AccountDto(
                                    a.getId(),
                                    a.getMoneyAmount()),
                                    Collectors.toList()
                            )
                    ));

            return users.stream()
                    .map(u -> new UserAccountDto(
                            u.getId(),
                            u.getLogin(),
                            userIdToAccounts.getOrDefault(
                                    u.getId(),
                                    Collections.emptyList())
                    ))
                    .collect(Collectors.toList());
        });
    }
}
