package ru.makan1.bankapp.model.dto;

import java.util.Objects;

public class AccountDto {
    private Long accountId;
    private Double moneyAmount;

    public AccountDto(Long accountId, Double moneyAmount) {
        this.accountId = accountId;
        this.moneyAmount = moneyAmount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(moneyAmount, that.moneyAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, moneyAmount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
