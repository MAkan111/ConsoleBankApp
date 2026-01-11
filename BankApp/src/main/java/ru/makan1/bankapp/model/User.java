package ru.makan1.bankapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class User {
    Long id;
    String login;
    List<Account> accountList = new ArrayList<>();
    private static final AtomicLong counter = new AtomicLong();

    public User() {
        this.id = counter.incrementAndGet();
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public List<Account> getAccountList() {
        return Collections.unmodifiableList(accountList);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(accountList, user.accountList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, accountList);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accounts=" + accountList +
                '}';
    }

    public void addAccount(Account account) {
        if(account == null) {
            throw new IllegalArgumentException("Счет не может быть null");
        }
        accountList.add(account);
    }

    public void removeAccountById(Long accountId) {
        accountList.removeIf(a -> Objects.equals(a.getId(), accountId));
    }
}
