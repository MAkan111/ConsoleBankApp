package ru.makan1.bankapp.service.menu.commands;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.enums.MenuCommand;
import ru.makan1.bankapp.service.menu.Command;
import ru.makan1.bankapp.service.menu.CommandContext;
import ru.makan1.bankapp.service.menu.ConsoleInput;

@Component
public class UserCreateCommand implements Command {

    @Override
    public MenuCommand command() {
        return MenuCommand.USER_CREATE;
    }

    @Override
    public String description() {
        return "Создание нового пользователя";
    }

    @Override
    public void execute(CommandContext context) throws Exception {
        String login = ConsoleInput.ask(context.getBufferedReader(), "Введите логин: ");
        context.getUserService().createUser(login);
        System.out.println("Пользователь создан");
    }
}
