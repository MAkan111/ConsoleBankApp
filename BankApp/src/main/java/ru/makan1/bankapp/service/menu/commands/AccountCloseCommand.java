package ru.makan1.bankapp.service.menu.commands;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.enums.MenuCommand;
import ru.makan1.bankapp.service.menu.Command;
import ru.makan1.bankapp.service.menu.CommandContext;
import ru.makan1.bankapp.service.menu.ConsoleInput;

@Component
public class AccountCloseCommand implements Command {

    @Override
    public MenuCommand command() {
        return MenuCommand.ACCOUNT_CLOSE;
    }

    @Override
    public String description() {
        return "Закрытие счета";
    }

    @Override
    public void execute(CommandContext context) throws Exception {
        long accountId = ConsoleInput.askPositiveLong(context.getBufferedReader(), "Введите номер счёта который надо закрыть: ");
        context.getAccountService().closeAccount(accountId);
        System.out.println("Счет " + accountId + " закрыт");
    }
}
