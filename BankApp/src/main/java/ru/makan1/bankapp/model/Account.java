package ru.makan1.bankapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    Long id;

    @Column(name = "account_money_amount")
    Double moneyAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    public Account() {
    }

    public Account(Long id, Double moneyAmount, User user) {
        this.id = id;
        this.moneyAmount = moneyAmount;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(moneyAmount, account.moneyAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, moneyAmount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "moneyAmount=" + moneyAmount +
                ", id=" + id +
                '}';
    }
}
