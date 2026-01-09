package ru.makan1.bankapp.service;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.config.AccountProperties;
import ru.makan1.bankapp.exception.AccountNotFoundException;
import ru.makan1.bankapp.exception.UserNotFoundException;
import ru.makan1.bankapp.model.Account;
import ru.makan1.bankapp.model.User;
import ru.makan1.bankapp.repository.InMemoryAccountRepository;
import ru.makan1.bankapp.repository.InMemoryUserRepository;

import java.util.*;

@Component
public class AccountService {
    private final AccountProperties accountProperties;
    private final InMemoryAccountRepository inMemoryAccountRepository;
    private final InMemoryUserRepository inMemoryUserRepository;

    public AccountService(AccountProperties accountProperties, InMemoryAccountRepository inMemoryAccountRepository, InMemoryUserRepository inMemoryUserRepository) {
        this.accountProperties = accountProperties;
        this.inMemoryAccountRepository = inMemoryAccountRepository;
        this.inMemoryUserRepository = inMemoryUserRepository;
    }

    public void createAccount(Long userId) {
        User user = inMemoryUserRepository.findById(userId);
        if (user == null) {
            throw new UserNotFoundException("Пользователь c userId: " + userId + " не найден");
        }

        Account account = new Account();
        account.setUserId(userId);
        account.setMoneyAmount(accountProperties.getDefaultAmount());

        inMemoryAccountRepository.save(account);

        if (user.getAccountList() == null) {
            user.setAccountList(new ArrayList<>());
        }

        user.getAccountList().add(account);

        inMemoryUserRepository.save(user);
    }

    public void closeAccount(Long accountId) {
        Account accountToClose = inMemoryAccountRepository.findById(accountId);
        if (accountToClose == null) {
            throw new IllegalArgumentException("Счет не найден accountId " + accountId);
        }

        User user = inMemoryUserRepository.findById(accountToClose.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("Пользователь для счета accountId: " + accountToClose.getUserId() + " не найден");
        }

        List<Account> userAccounts = inMemoryAccountRepository.findByUserId(accountToClose.getUserId());

        if (userAccounts.size() <= 1) {
            throw new IllegalArgumentException("Нельзя закрыть единственный счет пользователя");
        }

        Account target = userAccounts.stream()
                .filter(a -> !Objects.equals(a.getId(), accountId))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Не найден счет для перевода остатка"));

        double accountAmount = accountToClose.getMoneyAmount() == null ? 0.0 : accountToClose.getMoneyAmount();
        if (accountAmount > 0) {
            accountTransfer(accountId, target.getId(), accountAmount);
        }

        inMemoryAccountRepository.deleteById(accountId);

        if (user.getAccountList() != null) {
            user.getAccountList().removeIf(a -> Objects.equals(a.getId(), accountId));
        }

        inMemoryUserRepository.save(user);
    }

    public void accountDeposit(Long userId, Double amount) {
        Account account = inMemoryAccountRepository.findByUserId(userId).get(0);
        if (account == null) {
            throw new AccountNotFoundException("Счет для пользователя userId: " + userId + " не найден");
        }
        account.setMoneyAmount(account.getMoneyAmount() + amount);
        inMemoryAccountRepository.save(account);
    }

    public void accountTransfer(Long accountIdFrom, Long accountIdTo, Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Сумма должна перевода быть больше 0");
        }
        Account accountFrom = inMemoryAccountRepository.findById(accountIdFrom);
        Account accountTo = inMemoryAccountRepository.findById(accountIdTo);
        double commission = 0.0;

        boolean differentUsers = !Objects.equals(accountFrom.getUserId(), accountTo.getUserId());
        if (differentUsers) {
            commission = accountProperties.getTransferCommission();
        }

        double total = amount + (amount * commission);

        if (accountFrom.getMoneyAmount() < total) {
            throw new IllegalArgumentException("Недостаточно средств на счете отправителя. Нужно " + total);
        }

        accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - total);
        accountTo.setMoneyAmount(accountTo.getMoneyAmount() + amount);

        inMemoryAccountRepository.save(accountFrom);
        inMemoryAccountRepository.save(accountTo);
    }

    public void accountWithdraw(Long userId, Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Сумма не может быть null или меньше/равна нулю");
        }

        Account account = inMemoryAccountRepository.findById(userId);
        if (amount > account.getMoneyAmount()) {
            throw new IllegalArgumentException("Недостаточно средств на счете");
        }
        account.setMoneyAmount(account.getMoneyAmount() - amount);
        inMemoryAccountRepository.save(account);
    }
}
