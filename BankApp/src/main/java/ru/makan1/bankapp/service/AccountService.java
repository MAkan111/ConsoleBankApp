package ru.makan1.bankapp.service;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.config.AccountProperties;
import ru.makan1.bankapp.exception.AccountNotFoundException;
import ru.makan1.bankapp.exception.UserNotFoundException;
import ru.makan1.bankapp.model.Account;
import ru.makan1.bankapp.model.User;
import ru.makan1.bankapp.repository.database.DBAccountRepository;
import ru.makan1.bankapp.repository.database.DBUserRepository;

import java.util.*;

@Component
public class AccountService {
    private final AccountProperties accountProperties;
    private final DBUserRepository dbUserRepository;
    private final DBAccountRepository dbAccountRepository;
    private final TransactionHelper transactionHelper;

    public AccountService(AccountProperties accountProperties,
                          DBUserRepository dbUserRepository,
                          DBAccountRepository dbAccountRepository,
                          TransactionHelper transactionHelper
    ) {
        this.accountProperties = accountProperties;
        this.dbUserRepository = dbUserRepository;
        this.dbAccountRepository = dbAccountRepository;
        this.transactionHelper = transactionHelper;
    }

    public void createAccount(Long userId) {
        transactionHelper.execute(() -> {
            User user = dbUserRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("Пользователь c userId: " + userId + " не найден"));

            Account account = new Account();
            account.setMoneyAmount(accountProperties.getDefaultAmount());
            account.setUser(user);

            dbAccountRepository.save(account);
        });
    }

    public void closeAccount(Long accountId) {
        transactionHelper.execute(() -> {
            Account accountToClose = dbAccountRepository.findById(accountId)
                    .orElseThrow(() -> new AccountNotFoundException("Счет не найден accountId " + accountId));

            User user = accountToClose.getUser();
            List<Account> userAccounts = user.getAccountList();

            if (userAccounts.size() <= 1) {
                throw new IllegalArgumentException("Нельзя закрыть единственный счет пользователя");
            }

            Account target = userAccounts.stream()
                    .filter(a -> !Objects.equals(a.getId(), accountId))
                    .findFirst()
                    .orElseThrow(() -> new AccountNotFoundException("Не найден счет для перевода остатка"));

            double amount = accountToClose.getMoneyAmount() == null ? 0d : accountToClose.getMoneyAmount();
            target.setMoneyAmount(target.getMoneyAmount() + amount);

            dbAccountRepository.deleteById(accountId);
        });
    }

    public void accountDeposit(Long accountId, Double amount) {
        transactionHelper.execute(() -> {
            Account account = dbAccountRepository.findById(accountId)
                    .orElseThrow(() -> new AccountNotFoundException("Счет: " + accountId + " не найден"));

            account.setMoneyAmount(account.getMoneyAmount() + amount);
            dbAccountRepository.update(account);
        });
    }

    public void accountTransfer(Long accountIdFrom, Long accountIdTo, Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Сумма должна перевода быть больше 0");
        }

        transactionHelper.execute(() -> {
            Account accountFrom = dbAccountRepository.findById(accountIdFrom)
                    .orElseThrow(() -> new AccountNotFoundException("Счет не найден accountId " + accountIdFrom));

            Account accountTo = dbAccountRepository.findById(accountIdTo)
                    .orElseThrow(() -> new AccountNotFoundException("Счет не найден accountId " + accountIdTo));

            double commission = 0.0;

            boolean differentUsers = !Objects.equals(accountFrom.getUser().getId(), accountTo.getUser().getId());
            if (differentUsers) {
                commission = accountProperties.getTransferCommission();
            }

            double total = amount + (amount * commission);

            if (accountFrom.getMoneyAmount() < total) {
                throw new IllegalArgumentException("Недостаточно средств на счете отправителя. Нужно " + total);
            }

            accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - total);
            accountTo.setMoneyAmount(accountTo.getMoneyAmount() + amount);

            dbAccountRepository.update(accountFrom);
            dbAccountRepository.update(accountTo);
        });
    }

    public void accountWithdraw(Long userId, Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Сумма не может быть null или меньше/равна нулю");
        }

        transactionHelper.execute(() -> {
            Account account = dbAccountRepository.findById(userId)
                    .orElseThrow(() -> new AccountNotFoundException("Счет не найден accountId " + userId));

            if (amount > account.getMoneyAmount()) {
                throw new IllegalArgumentException("Недостаточно средств на счете");
            }
            account.setMoneyAmount(account.getMoneyAmount() - amount);
            dbAccountRepository.update(account);
        });
    }
}
