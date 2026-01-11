package ru.makan1.bankapp.service.menu;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.service.AccountService;
import ru.makan1.bankapp.service.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class CommandContext {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final UserService userService;
    private final AccountService accountService;

    public CommandContext(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public UserService getUserService() {
        return userService;
    }

    public AccountService getAccountService() {
        return accountService;
    }
}
