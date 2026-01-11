package ru.makan1.bankapp.enums;

import java.util.Optional;

public enum MenuCommand {
    USER_CREATE,
    SHOW_ALL_USERS,
    ACCOUNT_CREATE,
    ACCOUNT_CLOSE,
    ACCOUNT_DEPOSIT,
    ACCOUNT_TRANSFER,
    ACCOUNT_WITHDRAW,
    HELP;

    public static Optional<MenuCommand> fromInput(String input) {
        try {
            return Optional.of(MenuCommand.valueOf(input.trim().toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
