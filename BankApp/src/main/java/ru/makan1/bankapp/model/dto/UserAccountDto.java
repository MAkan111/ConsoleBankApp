package ru.makan1.bankapp.model.dto;

import java.util.List;
import java.util.Objects;

public class UserAccountDto {
    private Long userId;
    private String login;
    private List<AccountDto> accounts;

    public UserAccountDto(
            Long userId,
            String login,
            List<AccountDto> accounts
    ) {
        this.userId = userId;
        this.login = login;
        this.accounts = accounts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<AccountDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDto> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserAccountDto that = (UserAccountDto) o;
        return Objects.equals(userId, that.userId) && Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
