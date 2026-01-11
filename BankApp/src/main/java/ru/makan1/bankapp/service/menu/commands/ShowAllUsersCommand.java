package ru.makan1.bankapp.service.menu.commands;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.enums.MenuCommand;
import ru.makan1.bankapp.service.menu.Command;
import ru.makan1.bankapp.service.menu.CommandContext;

@Component
public class ShowAllUsersCommand implements Command {

    @Override
    public MenuCommand command() {
        return MenuCommand.SHOW_ALL_USERS;
    }

    @Override
    public String description() {
        return "Отображение списка всех пользователей";
    }

    @Override
    public void execute(CommandContext context) throws Exception {
        System.out.println(context.getUserService().showAllUsers());
    }
}
