package ru.makan1.bankapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id;

    @Column(name = "user_login")
    String login;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,  orphanRemoval = true)
    List<Account> accountList = new ArrayList<>();

    public User() {
    }

    public User(Long id, String login, List<Account> accountList) {
        this.id = id;
        this.login = login;
        this.accountList = accountList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public void addAccount(Account account) {
        accountList.add(account);
        account.setUser(this);
    }

    public void removeAccount(Account account) {
        accountList.remove(account);
        account.setUser(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", id=" + id +
                '}';
    }
}
