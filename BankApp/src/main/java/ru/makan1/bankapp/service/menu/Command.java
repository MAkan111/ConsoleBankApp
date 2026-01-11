package ru.makan1.bankapp.service.menu;

import ru.makan1.bankapp.enums.MenuCommand;

public interface Command {
    MenuCommand command();

    String description();

    void execute(CommandContext context) throws Exception;
}
