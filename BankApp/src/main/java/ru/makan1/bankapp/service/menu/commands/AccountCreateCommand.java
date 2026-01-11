package ru.makan1.bankapp.service.menu.commands;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.enums.MenuCommand;
import ru.makan1.bankapp.service.menu.Command;
import ru.makan1.bankapp.service.menu.CommandContext;
import ru.makan1.bankapp.service.menu.ConsoleInput;

@Component
public class AccountCreateCommand implements Command {

    @Override
    public MenuCommand command() {
        return MenuCommand.ACCOUNT_CREATE;
    }

    @Override
    public String description() {
        return "Создание нового счета для пользователя";
    }

    @Override
    public void execute(CommandContext context) throws Exception {
        long userId = ConsoleInput.askPositiveLong(context.getBufferedReader(), "Введите id пользователя для которого нужно создать счет: ");
        context.getAccountService().createAccount(userId);
        System.out.println("Счет для пользователя " + userId + " создан");
    }
}
